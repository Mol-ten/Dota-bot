package kz.moltenhaze.lobbybot.handlers.dota2;

import com.google.protobuf.ByteString;
import in.dragonbra.javasteam.base.ClientGCMsgProtobuf;
import in.dragonbra.javasteam.base.IPacketGCMsg;
import kz.moltenhaze.lobbybot.callbacks.common.PopupCallback;
import kz.moltenhaze.lobbybot.callbacks.common.shared.*;
import kz.moltenhaze.lobbybot.util.CSOTypes;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTAPopup;
import kz.moltenhaze.proto.dota.DotaGcmessagesMsgid.EDOTAGCMsg;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgSOMultipleObjects;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgSOMultipleObjects.SingleObject;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgSOSingleObject;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgSOCacheUnsubscribed;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgClientWelcome;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgSOCacheSubscribed;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgSOCacheSubscribed.SubscribedType;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgSOIDOwner;
import kz.moltenhaze.proto.dota.Gcsystemmsgs.EGCBaseClientMsg;
import kz.moltenhaze.proto.dota.Gcsystemmsgs.ESOMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
public class Dota2SharedObjects extends Dota2GCMsgHandler {

    public enum ACTION {
        NEW, REMOVED, UPDATED
    }

    private final Map<Integer, Consumer<IPacketGCMsg>> dispatchMap;
    private final Map<CMsgSOIDOwner, List<SubscribedType>> cache;

    public Dota2SharedObjects() {
        dispatchMap =  new HashMap<>();
        cache = new ConcurrentHashMap<>();
        dispatchMap.put(ESOMsg.k_ESOMsg_Create_VALUE, this::handleCreate);
        dispatchMap.put(ESOMsg.k_ESOMsg_Update_VALUE, this::handleUpdate);
        dispatchMap.put(ESOMsg.k_ESOMsg_Destroy_VALUE, this::handleDestroy);
        dispatchMap.put(ESOMsg.k_ESOMsg_UpdateMultiple_VALUE, this::handleUpdateMultiple);
        dispatchMap.put(ESOMsg.k_ESOMsg_CacheSubscribed_VALUE, this::handleSubscribed);
        dispatchMap.put(ESOMsg.k_ESOMsg_CacheUnsubscribed_VALUE, this::handleUnsubscribed);
        dispatchMap.put(EGCBaseClientMsg.k_EMsgGCClientWelcome_VALUE, this::handleClientWelcome);
        dispatchMap.put(EDOTAGCMsg.k_EMsgGCPopup_VALUE, this::handlePopup);
    }

    private void handleCreate(IPacketGCMsg packetMsg) {
        ClientGCMsgProtobuf<CMsgSOSingleObject.Builder> single = new ClientGCMsgProtobuf<>(CMsgSOSingleObject.class,
                packetMsg);
        handleSingleObject(ACTION.NEW, single.getBody().getTypeId(), single.getBody().getObjectData());
    }

    private void handleUpdate(IPacketGCMsg packetMsg) {
        ClientGCMsgProtobuf<CMsgSOSingleObject.Builder> single = new ClientGCMsgProtobuf<>(CMsgSOSingleObject.class,
                packetMsg);
        handleSingleObject(ACTION.UPDATED, single.getBody().getTypeId(), single.getBody().getObjectData());
    }

    private void handleDestroy(IPacketGCMsg packetMsg) {
        ClientGCMsgProtobuf<CMsgSOSingleObject.Builder> single = new ClientGCMsgProtobuf<>(CMsgSOSingleObject.class,
                packetMsg);
        handleSingleObject(ACTION.REMOVED, single.getBody().getTypeId(), single.getBody().getObjectData());
    }

    private void handleUpdateMultiple(IPacketGCMsg packetMsg) {
        ClientGCMsgProtobuf<CMsgSOMultipleObjects.Builder> multiple = new ClientGCMsgProtobuf<>(
                CMsgSOMultipleObjects.class, packetMsg);

        for (SingleObject single : multiple.getBody().getObjectsModifiedList()) {
            handleSingleObject(ACTION.UPDATED, single.getTypeId(), single.getObjectData());
        }

        for (SingleObject single : multiple.getBody().getObjectsAddedList()) {
            handleSingleObject(ACTION.NEW, single.getTypeId(), single.getObjectData());
        }

        for (SingleObject single : multiple.getBody().getObjectsRemovedList()) {
            handleSingleObject(ACTION.REMOVED, single.getTypeId(), single.getObjectData());
        }
    }

    private void handleSubscribed(IPacketGCMsg packetGCMsg) {
        ClientGCMsgProtobuf<CMsgSOCacheSubscribed.Builder> subs = new ClientGCMsgProtobuf<>(
                CMsgSOCacheSubscribed.class, packetGCMsg);

        log.debug("handleSubscribed: {}", subs.getBody().getOwnerSoid());
        cache.put(subs.getBody().getOwnerSoid(), subs.getBody().getObjectsList());

        for (SubscribedType sub: subs.getBody().getObjectsList()) {
            for (ByteString data: sub.getObjectDataList()) {
                handleSingleObject(ACTION.NEW, sub.getTypeId(), data);
            }
        }
    }

    private void handleUnsubscribed(IPacketGCMsg packetMsg) {
        ClientGCMsgProtobuf<CMsgSOCacheUnsubscribed.Builder> unsubs = new ClientGCMsgProtobuf<>(
                CMsgSOCacheUnsubscribed.class, packetMsg);

        log.debug("handleUnsubscribed: {}", unsubs.getBody().getOwnerSoid());
        List<SubscribedType> subs = cache.get(unsubs.getBody().getOwnerSoid());
        if (subs != null) {
            for (SubscribedType sub : subs) {
                for (ByteString data : sub.getObjectDataList()) {
                    handleSingleObject(ACTION.REMOVED, sub.getTypeId(), data);
                }
            }
        }
    }

    private void handleSingleObject(ACTION action, int typeId, ByteString data) {
        switch (action) {
            case NEW -> {
                dota2Client.postCallback(new SingleObjectNewLobby(typeId, data));
                dota2Client.postCallback(new SingleObjectNewParty(typeId, data));
            }

            case REMOVED -> {
                dota2Client.postCallback(new SingleObjectRemovedLobby(typeId, data));
                dota2Client.postCallback(new SingleObjectRemovedParty(typeId, data));
            }

            case UPDATED -> {
                dota2Client.postCallback(new SingleObjectUpdatedLobby(typeId, data));
                dota2Client.postCallback(new SingleObjectUpdatedParty(typeId, data));
            }
        }
    }

    private void handleClientWelcome(IPacketGCMsg packetMsg) {
        ClientGCMsgProtobuf<CMsgClientWelcome.Builder> welcome = new ClientGCMsgProtobuf<>(CMsgClientWelcome.class,
                packetMsg);

        for (CMsgSOCacheSubscribed subs : welcome.getBody().getOutofdateSubscribedCachesList()) {
            log.debug("handleClientWelcome: {}", subs.getOwnerSoid());
            cache.put(subs.getOwnerSoid(), subs.getObjectsList());
            boolean lobbyExist = false;
            for (SubscribedType sub : subs.getObjectsList()) {
                for (ByteString data : sub.getObjectDataList()) {
                    handleSingleObject(ACTION.NEW, sub.getTypeId(), data);
                    if (CSOTypes.LOBBY_VALUE == sub.getTypeId()) {
                        lobbyExist = true;

                        dota2Client.registerAndWait(sub.getTypeId());
                    }
                }
            }
            if (!lobbyExist && dota2Client.getLobbyHandler().getLobby() != null) {
                dota2Client.postCallback(new SingleObjectRemovedLobby(CSOTypes.LOBBY_VALUE, ByteString.EMPTY));
            }
        }
    }

    private void handlePopup(IPacketGCMsg data) {
        ClientGCMsgProtobuf<CMsgDOTAPopup.Builder> protobuf = new ClientGCMsgProtobuf<>(CMsgDOTAPopup.class, data);
        log.debug("handlePopup: {}/{}", protobuf.getBody(), data.getTargetJobID());
        dota2Client.postCallback(new PopupCallback(data.getTargetJobID(), protobuf.getBody()));
    }

    @Override
    public void handleGCMsg(IPacketGCMsg packetGCMsg) {
        Consumer<IPacketGCMsg> dispatcher = dispatchMap.get(packetGCMsg.getMsgType());

        if (dispatcher != null) {
            dispatcher.accept(packetGCMsg);
        }
    }
}
