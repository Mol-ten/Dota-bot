package kz.moltenhaze.lobbybot.handlers.dota2;

import in.dragonbra.javasteam.base.ClientGCMsgProtobuf;
import in.dragonbra.javasteam.base.IPacketGCMsg;
import kz.moltenhaze.lobbybot.callbacks.common.match.MatchDetailsCallback;
import kz.moltenhaze.lobbybot.callbacks.common.match.MatchMakingStatsCallback;
import kz.moltenhaze.lobbybot.callbacks.common.match.MatchSignedOutCallback;
import kz.moltenhaze.lobbybot.callbacks.common.match.RequestMatchesCallback;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTAMatchmakingStatsRequest;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTAMatchmakingStatsResponse;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTARequestMatchesResponse;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgGCToClientMatchSignedOut;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgGCMatchDetailsResponse;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgGCMatchDetailsRequest;
import kz.moltenhaze.proto.dota.DotaGcmessagesMsgid.EDOTAGCMsg;
import lombok.extern.slf4j.Slf4j;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
public class Dota2Match extends Dota2GCMsgHandler {
    private final Map<Integer, Consumer<IPacketGCMsg>> dispatchMap;

    public Dota2Match() {
        dispatchMap = new HashMap<>();

        dispatchMap.put(EDOTAGCMsg.k_EMsgGCMatchmakingStatsResponse_VALUE, this::handleMMStats);
        dispatchMap.put(EDOTAGCMsg.k_EMsgGCMatchDetailsResponse_VALUE, this::handleMatchDetails);
        dispatchMap.put(EDOTAGCMsg.k_EMsgGCToClientMatchSignedOut_VALUE, this::handleMatchSignedOut);
    }

    @Override
    public void handleGCMsg(IPacketGCMsg packetGCMsg) {
        Consumer<IPacketGCMsg> consumer = dispatchMap.get(packetGCMsg.getMsgType());

        if (consumer != null) {
            log.info("handleGCMsg: {}", packetGCMsg.getMsgType());
            consumer.accept(packetGCMsg);
        }
    }

    private void handleMMStats(IPacketGCMsg msg) {
        ClientGCMsgProtobuf<CMsgDOTAMatchmakingStatsResponse.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgDOTAMatchmakingStatsResponse.class, msg);

        log.debug("handleMMStats: {}", protobuf.getBody());
        dota2Client.postCallback(new MatchMakingStatsCallback(protobuf.getBody()));
    }

    private void handleMatchDetails(IPacketGCMsg msg) {
        ClientGCMsgProtobuf<CMsgGCMatchDetailsResponse.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgGCMatchDetailsResponse.class, msg);

        log.info("handleMatchDetails: {}", protobuf.getBody());
        dota2Client.postCallback(new MatchDetailsCallback(protobuf.getTargetJobID(), protobuf.getBody()));
    }

    private void handleMatchSignedOut(IPacketGCMsg msg) {
        ClientGCMsgProtobuf<CMsgGCToClientMatchSignedOut.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgGCToClientMatchSignedOut.class, msg);

        log.debug("handleMatchSignedOut: {}", protobuf.getBody());
        dota2Client.postCallback(new MatchSignedOutCallback(protobuf.getBody()));
    }

    private void handleMatches(IPacketGCMsg msg) {
        ClientGCMsgProtobuf<CMsgDOTARequestMatchesResponse.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgDOTARequestMatchesResponse.class, msg);

        log.debug("handleMatches: {}", protobuf.getBody());
        dota2Client.postCallback(new RequestMatchesCallback(protobuf.getBody()));
    }



    public void requestMMStats() {
        ClientGCMsgProtobuf<CMsgDOTAMatchmakingStatsRequest.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgDOTAMatchmakingStatsRequest.class, EDOTAGCMsg.k_EMsgGCMatchmakingStatsRequest_VALUE);

        log.debug("requestMMStats: {}", protobuf.getBody());
        send(protobuf);
    }

    public MatchDetailsCallback requestMatchDetails(Long matchId) {
        ClientGCMsgProtobuf<CMsgGCMatchDetailsRequest.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgGCMatchDetailsRequest.class, EDOTAGCMsg.k_EMsgGCMatchDetailsRequest_VALUE);
        protobuf.getBody().setMatchId(matchId);

        log.debug("requestMatchDetails: {}", protobuf.getBody());
        return sendJobAndWait(protobuf);
    }
}
