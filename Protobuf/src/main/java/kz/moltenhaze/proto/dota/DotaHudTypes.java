// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: dota_hud_types.proto
// Protobuf Java Version: 4.28.0

package kz.moltenhaze.proto.dota;

public final class DotaHudTypes {
  private DotaHudTypes() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 0,
      /* suffix= */ "",
      DotaHudTypes.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
    registry.add(kz.moltenhaze.proto.dota.DotaHudTypes.hudLocalizeToken);
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * Protobuf enum {@code EHeroSelectionText}
   */
  public enum EHeroSelectionText
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>k_EHeroSelectionText_Invalid = -1;</code>
     */
    k_EHeroSelectionText_Invalid(-1),
    /**
     * <code>k_EHeroSelectionText_None = 0;</code>
     */
    k_EHeroSelectionText_None(0),
    /**
     * <code>k_EHeroSelectionText_ChooseHero = 1 [(.hud_localize_token) = "#DOTA_Hero_Selection_ChooseHero"];</code>
     */
    k_EHeroSelectionText_ChooseHero(1),
    /**
     * <code>k_EHeroSelectionText_AllDraft_Planning_YouFirst = 2 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_Planning_YouFirst"];</code>
     */
    k_EHeroSelectionText_AllDraft_Planning_YouFirst(2),
    /**
     * <code>k_EHeroSelectionText_AllDraft_Planning_TheyFirst = 3 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_Planning_TheyFirst"];</code>
     */
    k_EHeroSelectionText_AllDraft_Planning_TheyFirst(3),
    /**
     * <code>k_EHeroSelectionText_AllDraft_Banning = 4 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_Banning_Nominate"];</code>
     */
    k_EHeroSelectionText_AllDraft_Banning(4),
    /**
     * <code>k_EHeroSelectionText_AllDraft_Ban_Waiting = 5 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_Banning_Nominated"];</code>
     */
    k_EHeroSelectionText_AllDraft_Ban_Waiting(5),
    /**
     * <code>k_EHeroSelectionText_AllDraft_PickTwo = 6 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_PickPhase_PickTwo"];</code>
     */
    k_EHeroSelectionText_AllDraft_PickTwo(6),
    /**
     * <code>k_EHeroSelectionText_AllDraft_PickOneMore = 7 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_PickPhase_PickOneMore"];</code>
     */
    k_EHeroSelectionText_AllDraft_PickOneMore(7),
    /**
     * <code>k_EHeroSelectionText_AllDraft_PickOne = 8 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_PickPhase_PickOne"];</code>
     */
    k_EHeroSelectionText_AllDraft_PickOne(8),
    /**
     * <code>k_EHeroSelectionText_AllDraft_WaitingRadiant = 9 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_PickPhase_WaitingRadiant"];</code>
     */
    k_EHeroSelectionText_AllDraft_WaitingRadiant(9),
    /**
     * <code>k_EHeroSelectionText_AllDraft_WaitingDire = 10 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_PickPhase_WaitingDire"];</code>
     */
    k_EHeroSelectionText_AllDraft_WaitingDire(10),
    /**
     * <code>k_EHeroSelectionText_AllDraft_TeammateRandomed = 11 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_TeammateRandomed_Panorama"];</code>
     */
    k_EHeroSelectionText_AllDraft_TeammateRandomed(11),
    /**
     * <code>k_EHeroSelectionText_AllDraft_YouPicking_LosingGold = 12 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_YouPicking_LosingGold"];</code>
     */
    k_EHeroSelectionText_AllDraft_YouPicking_LosingGold(12),
    /**
     * <code>k_EHeroSelectionText_AllDraft_TheyPicking_LosingGold = 13 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_TheyPicking_LosingGold"];</code>
     */
    k_EHeroSelectionText_AllDraft_TheyPicking_LosingGold(13),
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_ChooseCaptain = 14 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_ChooseCaptain"];</code>
     */
    k_EHeroSelectionText_CaptainsMode_ChooseCaptain(14),
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_WaitingForChooseCaptain = 15 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_WaitingForChooseCaptain"];</code>
     */
    k_EHeroSelectionText_CaptainsMode_WaitingForChooseCaptain(15),
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_YouSelect = 16 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_YouSelect"];</code>
     */
    k_EHeroSelectionText_CaptainsMode_YouSelect(16),
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_TheySelect = 17 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_TheySelect"];</code>
     */
    k_EHeroSelectionText_CaptainsMode_TheySelect(17),
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_YouBan = 18 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_YouBan"];</code>
     */
    k_EHeroSelectionText_CaptainsMode_YouBan(18),
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_TheyBan = 19 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_TheyBan"];</code>
     */
    k_EHeroSelectionText_CaptainsMode_TheyBan(19),
    /**
     * <code>k_EHeroSelectionText_RandomDraft_HeroReview = 20 [(.hud_localize_token) = "#DOTA_Hero_Selection_RandomDraft_HeroReview"];</code>
     */
    k_EHeroSelectionText_RandomDraft_HeroReview(20),
    /**
     * <code>k_EHeroSelectionText_RandomDraft_RoundDisplay = 21 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_RoundDisplay"];</code>
     */
    k_EHeroSelectionText_RandomDraft_RoundDisplay(21),
    /**
     * <code>k_EHeroSelectionText_RandomDraft_Waiting = 22 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_Waiting"];</code>
     */
    k_EHeroSelectionText_RandomDraft_Waiting(22),
    /**
     * <code>k_EHeroSelectionText_EventGame_BanPhase = 23 [(.hud_localize_token) = "#DOTA_Hero_Selection_EventGame_BanPhase"];</code>
     */
    k_EHeroSelectionText_EventGame_BanPhase(23),
    ;

    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 28,
        /* patch= */ 0,
        /* suffix= */ "",
        EHeroSelectionText.class.getName());
    }
    /**
     * <code>k_EHeroSelectionText_Invalid = -1;</code>
     */
    public static final int k_EHeroSelectionText_Invalid_VALUE = -1;
    /**
     * <code>k_EHeroSelectionText_None = 0;</code>
     */
    public static final int k_EHeroSelectionText_None_VALUE = 0;
    /**
     * <code>k_EHeroSelectionText_ChooseHero = 1 [(.hud_localize_token) = "#DOTA_Hero_Selection_ChooseHero"];</code>
     */
    public static final int k_EHeroSelectionText_ChooseHero_VALUE = 1;
    /**
     * <code>k_EHeroSelectionText_AllDraft_Planning_YouFirst = 2 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_Planning_YouFirst"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_Planning_YouFirst_VALUE = 2;
    /**
     * <code>k_EHeroSelectionText_AllDraft_Planning_TheyFirst = 3 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_Planning_TheyFirst"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_Planning_TheyFirst_VALUE = 3;
    /**
     * <code>k_EHeroSelectionText_AllDraft_Banning = 4 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_Banning_Nominate"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_Banning_VALUE = 4;
    /**
     * <code>k_EHeroSelectionText_AllDraft_Ban_Waiting = 5 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_Banning_Nominated"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_Ban_Waiting_VALUE = 5;
    /**
     * <code>k_EHeroSelectionText_AllDraft_PickTwo = 6 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_PickPhase_PickTwo"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_PickTwo_VALUE = 6;
    /**
     * <code>k_EHeroSelectionText_AllDraft_PickOneMore = 7 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_PickPhase_PickOneMore"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_PickOneMore_VALUE = 7;
    /**
     * <code>k_EHeroSelectionText_AllDraft_PickOne = 8 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_PickPhase_PickOne"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_PickOne_VALUE = 8;
    /**
     * <code>k_EHeroSelectionText_AllDraft_WaitingRadiant = 9 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_PickPhase_WaitingRadiant"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_WaitingRadiant_VALUE = 9;
    /**
     * <code>k_EHeroSelectionText_AllDraft_WaitingDire = 10 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllPick_PickPhase_WaitingDire"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_WaitingDire_VALUE = 10;
    /**
     * <code>k_EHeroSelectionText_AllDraft_TeammateRandomed = 11 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_TeammateRandomed_Panorama"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_TeammateRandomed_VALUE = 11;
    /**
     * <code>k_EHeroSelectionText_AllDraft_YouPicking_LosingGold = 12 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_YouPicking_LosingGold"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_YouPicking_LosingGold_VALUE = 12;
    /**
     * <code>k_EHeroSelectionText_AllDraft_TheyPicking_LosingGold = 13 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_TheyPicking_LosingGold"];</code>
     */
    public static final int k_EHeroSelectionText_AllDraft_TheyPicking_LosingGold_VALUE = 13;
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_ChooseCaptain = 14 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_ChooseCaptain"];</code>
     */
    public static final int k_EHeroSelectionText_CaptainsMode_ChooseCaptain_VALUE = 14;
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_WaitingForChooseCaptain = 15 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_WaitingForChooseCaptain"];</code>
     */
    public static final int k_EHeroSelectionText_CaptainsMode_WaitingForChooseCaptain_VALUE = 15;
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_YouSelect = 16 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_YouSelect"];</code>
     */
    public static final int k_EHeroSelectionText_CaptainsMode_YouSelect_VALUE = 16;
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_TheySelect = 17 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_TheySelect"];</code>
     */
    public static final int k_EHeroSelectionText_CaptainsMode_TheySelect_VALUE = 17;
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_YouBan = 18 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_YouBan"];</code>
     */
    public static final int k_EHeroSelectionText_CaptainsMode_YouBan_VALUE = 18;
    /**
     * <code>k_EHeroSelectionText_CaptainsMode_TheyBan = 19 [(.hud_localize_token) = "#DOTA_Hero_Selection_CaptainsMode_TheyBan"];</code>
     */
    public static final int k_EHeroSelectionText_CaptainsMode_TheyBan_VALUE = 19;
    /**
     * <code>k_EHeroSelectionText_RandomDraft_HeroReview = 20 [(.hud_localize_token) = "#DOTA_Hero_Selection_RandomDraft_HeroReview"];</code>
     */
    public static final int k_EHeroSelectionText_RandomDraft_HeroReview_VALUE = 20;
    /**
     * <code>k_EHeroSelectionText_RandomDraft_RoundDisplay = 21 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_RoundDisplay"];</code>
     */
    public static final int k_EHeroSelectionText_RandomDraft_RoundDisplay_VALUE = 21;
    /**
     * <code>k_EHeroSelectionText_RandomDraft_Waiting = 22 [(.hud_localize_token) = "#DOTA_Hero_Selection_AllDraft_Waiting"];</code>
     */
    public static final int k_EHeroSelectionText_RandomDraft_Waiting_VALUE = 22;
    /**
     * <code>k_EHeroSelectionText_EventGame_BanPhase = 23 [(.hud_localize_token) = "#DOTA_Hero_Selection_EventGame_BanPhase"];</code>
     */
    public static final int k_EHeroSelectionText_EventGame_BanPhase_VALUE = 23;


    public final int getNumber() {
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static EHeroSelectionText valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static EHeroSelectionText forNumber(int value) {
      switch (value) {
        case -1: return k_EHeroSelectionText_Invalid;
        case 0: return k_EHeroSelectionText_None;
        case 1: return k_EHeroSelectionText_ChooseHero;
        case 2: return k_EHeroSelectionText_AllDraft_Planning_YouFirst;
        case 3: return k_EHeroSelectionText_AllDraft_Planning_TheyFirst;
        case 4: return k_EHeroSelectionText_AllDraft_Banning;
        case 5: return k_EHeroSelectionText_AllDraft_Ban_Waiting;
        case 6: return k_EHeroSelectionText_AllDraft_PickTwo;
        case 7: return k_EHeroSelectionText_AllDraft_PickOneMore;
        case 8: return k_EHeroSelectionText_AllDraft_PickOne;
        case 9: return k_EHeroSelectionText_AllDraft_WaitingRadiant;
        case 10: return k_EHeroSelectionText_AllDraft_WaitingDire;
        case 11: return k_EHeroSelectionText_AllDraft_TeammateRandomed;
        case 12: return k_EHeroSelectionText_AllDraft_YouPicking_LosingGold;
        case 13: return k_EHeroSelectionText_AllDraft_TheyPicking_LosingGold;
        case 14: return k_EHeroSelectionText_CaptainsMode_ChooseCaptain;
        case 15: return k_EHeroSelectionText_CaptainsMode_WaitingForChooseCaptain;
        case 16: return k_EHeroSelectionText_CaptainsMode_YouSelect;
        case 17: return k_EHeroSelectionText_CaptainsMode_TheySelect;
        case 18: return k_EHeroSelectionText_CaptainsMode_YouBan;
        case 19: return k_EHeroSelectionText_CaptainsMode_TheyBan;
        case 20: return k_EHeroSelectionText_RandomDraft_HeroReview;
        case 21: return k_EHeroSelectionText_RandomDraft_RoundDisplay;
        case 22: return k_EHeroSelectionText_RandomDraft_Waiting;
        case 23: return k_EHeroSelectionText_EventGame_BanPhase;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<EHeroSelectionText>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        EHeroSelectionText> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<EHeroSelectionText>() {
            public EHeroSelectionText findValueByNumber(int number) {
              return EHeroSelectionText.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return kz.moltenhaze.proto.dota.DotaHudTypes.getDescriptor().getEnumTypes().get(0);
    }

    private static final EHeroSelectionText[] VALUES = values();

    public static EHeroSelectionText valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private EHeroSelectionText(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:EHeroSelectionText)
  }

  public static final int HUD_LOCALIZE_TOKEN_FIELD_NUMBER = 51501;
  /**
   * <code>extend .google.protobuf.EnumValueOptions { ... }</code>
   */
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      com.google.protobuf.DescriptorProtos.EnumValueOptions,
      java.lang.String> hudLocalizeToken = com.google.protobuf.GeneratedMessage
          .newFileScopedGeneratedExtension(
        java.lang.String.class,
        null);

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024dota_hud_types.proto\032 google/protobuf/" +
      "descriptor.proto*\357\022\n\022EHeroSelectionText\022" +
      ")\n\034k_EHeroSelectionText_Invalid\020\377\377\377\377\377\377\377\377" +
      "\377\001\022\035\n\031k_EHeroSelectionText_None\020\000\022H\n\037k_E" +
      "HeroSelectionText_ChooseHero\020\001\032#\352\222\031\037#DOT" +
      "A_Hero_Selection_ChooseHero\022h\n/k_EHeroSe" +
      "lectionText_AllDraft_Planning_YouFirst\020\002" +
      "\0323\352\222\031/#DOTA_Hero_Selection_AllDraft_Plan" +
      "ning_YouFirst\022j\n0k_EHeroSelectionText_Al" +
      "lDraft_Planning_TheyFirst\020\003\0324\352\222\0310#DOTA_H" +
      "ero_Selection_AllDraft_Planning_TheyFirs" +
      "t\022\\\n%k_EHeroSelectionText_AllDraft_Banni" +
      "ng\020\004\0321\352\222\031-#DOTA_Hero_Selection_AllPick_B" +
      "anning_Nominate\022a\n)k_EHeroSelectionText_" +
      "AllDraft_Ban_Waiting\020\005\0322\352\222\031.#DOTA_Hero_S" +
      "election_AllPick_Banning_Nominated\022]\n%k_" +
      "EHeroSelectionText_AllDraft_PickTwo\020\006\0322\352" +
      "\222\031.#DOTA_Hero_Selection_AllPick_PickPhas" +
      "e_PickTwo\022e\n)k_EHeroSelectionText_AllDra" +
      "ft_PickOneMore\020\007\0326\352\222\0312#DOTA_Hero_Selecti" +
      "on_AllPick_PickPhase_PickOneMore\022]\n%k_EH" +
      "eroSelectionText_AllDraft_PickOne\020\010\0322\352\222\031" +
      ".#DOTA_Hero_Selection_AllPick_PickPhase_" +
      "PickOne\022k\n,k_EHeroSelectionText_AllDraft" +
      "_WaitingRadiant\020\t\0329\352\222\0315#DOTA_Hero_Select" +
      "ion_AllPick_PickPhase_WaitingRadiant\022e\n)" +
      "k_EHeroSelectionText_AllDraft_WaitingDir" +
      "e\020\n\0326\352\222\0312#DOTA_Hero_Selection_AllPick_Pi" +
      "ckPhase_WaitingDire\022o\n.k_EHeroSelectionT" +
      "ext_AllDraft_TeammateRandomed\020\013\032;\352\222\0317#DO" +
      "TA_Hero_Selection_AllDraft_TeammateRando" +
      "med_Panorama\022p\n3k_EHeroSelectionText_All" +
      "Draft_YouPicking_LosingGold\020\014\0327\352\222\0313#DOTA" +
      "_Hero_Selection_AllDraft_YouPicking_Losi" +
      "ngGold\022r\n4k_EHeroSelectionText_AllDraft_" +
      "TheyPicking_LosingGold\020\r\0328\352\222\0314#DOTA_Hero" +
      "_Selection_AllDraft_TheyPicking_LosingGo" +
      "ld\022h\n/k_EHeroSelectionText_CaptainsMode_" +
      "ChooseCaptain\020\016\0323\352\222\031/#DOTA_Hero_Selectio" +
      "n_CaptainsMode_ChooseCaptain\022|\n9k_EHeroS" +
      "electionText_CaptainsMode_WaitingForChoo" +
      "seCaptain\020\017\032=\352\222\0319#DOTA_Hero_Selection_Ca" +
      "ptainsMode_WaitingForChooseCaptain\022`\n+k_" +
      "EHeroSelectionText_CaptainsMode_YouSelec" +
      "t\020\020\032/\352\222\031+#DOTA_Hero_Selection_CaptainsMo" +
      "de_YouSelect\022b\n,k_EHeroSelectionText_Cap" +
      "tainsMode_TheySelect\020\021\0320\352\222\031,#DOTA_Hero_S" +
      "election_CaptainsMode_TheySelect\022Z\n(k_EH" +
      "eroSelectionText_CaptainsMode_YouBan\020\022\032," +
      "\352\222\031(#DOTA_Hero_Selection_CaptainsMode_Yo" +
      "uBan\022\\\n)k_EHeroSelectionText_CaptainsMod" +
      "e_TheyBan\020\023\032-\352\222\031)#DOTA_Hero_Selection_Ca" +
      "ptainsMode_TheyBan\022`\n+k_EHeroSelectionTe" +
      "xt_RandomDraft_HeroReview\020\024\032/\352\222\031+#DOTA_H" +
      "ero_Selection_RandomDraft_HeroReview\022a\n-" +
      "k_EHeroSelectionText_RandomDraft_RoundDi" +
      "splay\020\025\032.\352\222\031*#DOTA_Hero_Selection_AllDra" +
      "ft_RoundDisplay\022W\n(k_EHeroSelectionText_" +
      "RandomDraft_Waiting\020\026\032)\352\222\031%#DOTA_Hero_Se" +
      "lection_AllDraft_Waiting\022X\n\'k_EHeroSelec" +
      "tionText_EventGame_BanPhase\020\027\032+\352\222\031\'#DOTA" +
      "_Hero_Selection_EventGame_BanPhase:?\n\022hu" +
      "d_localize_token\022!.google.protobuf.EnumV" +
      "alueOptions\030\255\222\003 \001(\tB\032\n\030kz.moltenhaze.pro" +
      "to.dota"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.DescriptorProtos.getDescriptor(),
        });
    hudLocalizeToken.internalInit(descriptor.getExtensions().get(0));
    descriptor.resolveAllFeaturesImmutable();
    com.google.protobuf.DescriptorProtos.getDescriptor();
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(kz.moltenhaze.proto.dota.DotaHudTypes.hudLocalizeToken);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
  }

  // @@protoc_insertion_point(outer_class_scope)
}