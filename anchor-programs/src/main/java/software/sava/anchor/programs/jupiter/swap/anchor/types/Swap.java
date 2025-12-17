package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.Borsh;
import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public sealed interface Swap extends RustEnum permits
  Swap.Saber,
  Swap.SaberAddDecimalsDeposit,
  Swap.SaberAddDecimalsWithdraw,
  Swap.TokenSwap,
  Swap.Sencha,
  Swap.Step,
  Swap.Cropper,
  Swap.Raydium,
  Swap.Crema,
  Swap.Lifinity,
  Swap.Mercurial,
  Swap.Cykura,
  Swap.Serum,
  Swap.MarinadeDeposit,
  Swap.MarinadeUnstake,
  Swap.Aldrin,
  Swap.AldrinV2,
  Swap.Whirlpool,
  Swap.Invariant,
  Swap.Meteora,
  Swap.GooseFX,
  Swap.DeltaFi,
  Swap.Balansol,
  Swap.MarcoPolo,
  Swap.Dradex,
  Swap.LifinityV2,
  Swap.RaydiumClmm,
  Swap.Openbook,
  Swap.Phoenix,
  Swap.Symmetry,
  Swap.TokenSwapV2,
  Swap.HeliumTreasuryManagementRedeemV0,
  Swap.StakeDexStakeWrappedSol,
  Swap.StakeDexSwapViaStake,
  Swap.GooseFXV2,
  Swap.Perps,
  Swap.PerpsAddLiquidity,
  Swap.PerpsRemoveLiquidity,
  Swap.MeteoraDlmm,
  Swap.OpenBookV2,
  Swap.RaydiumClmmV2,
  Swap.StakeDexPrefundWithdrawStakeAndDepositStake,
  Swap.Clone,
  Swap.SanctumS,
  Swap.SanctumSAddLiquidity,
  Swap.SanctumSRemoveLiquidity,
  Swap.RaydiumCP,
  Swap.WhirlpoolSwapV2,
  Swap.OneIntro,
  Swap.PumpWrappedBuy,
  Swap.PumpWrappedSell,
  Swap.PerpsV2,
  Swap.PerpsV2AddLiquidity,
  Swap.PerpsV2RemoveLiquidity,
  Swap.MoonshotWrappedBuy,
  Swap.MoonshotWrappedSell,
  Swap.StabbleStableSwap,
  Swap.StabbleWeightedSwap,
  Swap.Obric,
  Swap.FoxBuyFromEstimatedCost,
  Swap.FoxClaimPartial,
  Swap.SolFi,
  Swap.SolayerDelegateNoInit,
  Swap.SolayerUndelegateNoInit,
  Swap.TokenMill,
  Swap.DaosFunBuy,
  Swap.DaosFunSell,
  Swap.ZeroFi,
  Swap.StakeDexWithdrawWrappedSol,
  Swap.VirtualsBuy,
  Swap.VirtualsSell,
  Swap.Perena,
  Swap.PumpSwapBuy,
  Swap.PumpSwapSell,
  Swap.Gamma,
  Swap.MeteoraDlmmSwapV2,
  Swap.Woofi,
  Swap.MeteoraDammV2,
  Swap.MeteoraDynamicBondingCurveSwap,
  Swap.StabbleStableSwapV2,
  Swap.StabbleWeightedSwapV2,
  Swap.RaydiumLaunchlabBuy,
  Swap.RaydiumLaunchlabSell,
  Swap.BoopdotfunWrappedBuy,
  Swap.BoopdotfunWrappedSell,
  Swap.Plasma,
  Swap.GoonFi,
  Swap.HumidiFi,
  Swap.MeteoraDynamicBondingCurveSwapWithRemainingAccounts,
  Swap.TesseraV,
  Swap.PumpWrappedBuyV2,
  Swap.PumpWrappedSellV2,
  Swap.PumpSwapBuyV2,
  Swap.PumpSwapSellV2,
  Swap.Heaven,
  Swap.SolFiV2,
  Swap.Aquifer,
  Swap.PumpWrappedBuyV3,
  Swap.PumpWrappedSellV3,
  Swap.PumpSwapBuyV3,
  Swap.PumpSwapSellV3,
  Swap.JupiterLendDeposit,
  Swap.JupiterLendRedeem,
  Swap.DefiTuna,
  Swap.AlphaQ,
  Swap.RaydiumV2,
  Swap.SarosDlmm,
  Swap.Futarchy,
  Swap.MeteoraDammV2WithRemainingAccounts,
  Swap.Obsidian,
  Swap.WhaleStreet,
  Swap.DynamicV1,
  Swap.PumpWrappedBuyV4,
  Swap.PumpWrappedSellV4,
  Swap.CarrotIssue,
  Swap.CarrotRedeem,
  Swap.Manifest,
  Swap.BisonFi,
  Swap.HumidiFiV2 {

  static Swap read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Saber.INSTANCE;
      case 1 -> SaberAddDecimalsDeposit.INSTANCE;
      case 2 -> SaberAddDecimalsWithdraw.INSTANCE;
      case 3 -> TokenSwap.INSTANCE;
      case 4 -> Sencha.INSTANCE;
      case 5 -> Step.INSTANCE;
      case 6 -> Cropper.INSTANCE;
      case 7 -> Raydium.INSTANCE;
      case 8 -> Crema.read(_data, i);
      case 9 -> Lifinity.INSTANCE;
      case 10 -> Mercurial.INSTANCE;
      case 11 -> Cykura.INSTANCE;
      case 12 -> Serum.read(_data, i);
      case 13 -> MarinadeDeposit.INSTANCE;
      case 14 -> MarinadeUnstake.INSTANCE;
      case 15 -> Aldrin.read(_data, i);
      case 16 -> AldrinV2.read(_data, i);
      case 17 -> Whirlpool.read(_data, i);
      case 18 -> Invariant.read(_data, i);
      case 19 -> Meteora.INSTANCE;
      case 20 -> GooseFX.INSTANCE;
      case 21 -> DeltaFi.read(_data, i);
      case 22 -> Balansol.INSTANCE;
      case 23 -> MarcoPolo.read(_data, i);
      case 24 -> Dradex.read(_data, i);
      case 25 -> LifinityV2.INSTANCE;
      case 26 -> RaydiumClmm.INSTANCE;
      case 27 -> Openbook.read(_data, i);
      case 28 -> Phoenix.read(_data, i);
      case 29 -> Symmetry.read(_data, i);
      case 30 -> TokenSwapV2.INSTANCE;
      case 31 -> HeliumTreasuryManagementRedeemV0.INSTANCE;
      case 32 -> StakeDexStakeWrappedSol.INSTANCE;
      case 33 -> StakeDexSwapViaStake.read(_data, i);
      case 34 -> GooseFXV2.INSTANCE;
      case 35 -> Perps.INSTANCE;
      case 36 -> PerpsAddLiquidity.INSTANCE;
      case 37 -> PerpsRemoveLiquidity.INSTANCE;
      case 38 -> MeteoraDlmm.INSTANCE;
      case 39 -> OpenBookV2.read(_data, i);
      case 40 -> RaydiumClmmV2.INSTANCE;
      case 41 -> StakeDexPrefundWithdrawStakeAndDepositStake.read(_data, i);
      case 42 -> Clone.read(_data, i);
      case 43 -> SanctumS.read(_data, i);
      case 44 -> SanctumSAddLiquidity.read(_data, i);
      case 45 -> SanctumSRemoveLiquidity.read(_data, i);
      case 46 -> RaydiumCP.INSTANCE;
      case 47 -> WhirlpoolSwapV2.read(_data, i);
      case 48 -> OneIntro.INSTANCE;
      case 49 -> PumpWrappedBuy.INSTANCE;
      case 50 -> PumpWrappedSell.INSTANCE;
      case 51 -> PerpsV2.INSTANCE;
      case 52 -> PerpsV2AddLiquidity.INSTANCE;
      case 53 -> PerpsV2RemoveLiquidity.INSTANCE;
      case 54 -> MoonshotWrappedBuy.INSTANCE;
      case 55 -> MoonshotWrappedSell.INSTANCE;
      case 56 -> StabbleStableSwap.INSTANCE;
      case 57 -> StabbleWeightedSwap.INSTANCE;
      case 58 -> Obric.read(_data, i);
      case 59 -> FoxBuyFromEstimatedCost.INSTANCE;
      case 60 -> FoxClaimPartial.read(_data, i);
      case 61 -> SolFi.read(_data, i);
      case 62 -> SolayerDelegateNoInit.INSTANCE;
      case 63 -> SolayerUndelegateNoInit.INSTANCE;
      case 64 -> TokenMill.read(_data, i);
      case 65 -> DaosFunBuy.INSTANCE;
      case 66 -> DaosFunSell.INSTANCE;
      case 67 -> ZeroFi.INSTANCE;
      case 68 -> StakeDexWithdrawWrappedSol.INSTANCE;
      case 69 -> VirtualsBuy.INSTANCE;
      case 70 -> VirtualsSell.INSTANCE;
      case 71 -> Perena.read(_data, i);
      case 72 -> PumpSwapBuy.INSTANCE;
      case 73 -> PumpSwapSell.INSTANCE;
      case 74 -> Gamma.INSTANCE;
      case 75 -> MeteoraDlmmSwapV2.read(_data, i);
      case 76 -> Woofi.INSTANCE;
      case 77 -> MeteoraDammV2.INSTANCE;
      case 78 -> MeteoraDynamicBondingCurveSwap.INSTANCE;
      case 79 -> StabbleStableSwapV2.INSTANCE;
      case 80 -> StabbleWeightedSwapV2.INSTANCE;
      case 81 -> RaydiumLaunchlabBuy.read(_data, i);
      case 82 -> RaydiumLaunchlabSell.read(_data, i);
      case 83 -> BoopdotfunWrappedBuy.INSTANCE;
      case 84 -> BoopdotfunWrappedSell.INSTANCE;
      case 85 -> Plasma.read(_data, i);
      case 86 -> GoonFi.read(_data, i);
      case 87 -> HumidiFi.read(_data, i);
      case 88 -> MeteoraDynamicBondingCurveSwapWithRemainingAccounts.INSTANCE;
      case 89 -> TesseraV.read(_data, i);
      case 90 -> PumpWrappedBuyV2.INSTANCE;
      case 91 -> PumpWrappedSellV2.INSTANCE;
      case 92 -> PumpSwapBuyV2.INSTANCE;
      case 93 -> PumpSwapSellV2.INSTANCE;
      case 94 -> Heaven.read(_data, i);
      case 95 -> SolFiV2.read(_data, i);
      case 96 -> Aquifer.INSTANCE;
      case 97 -> PumpWrappedBuyV3.INSTANCE;
      case 98 -> PumpWrappedSellV3.INSTANCE;
      case 99 -> PumpSwapBuyV3.INSTANCE;
      case 100 -> PumpSwapSellV3.INSTANCE;
      case 101 -> JupiterLendDeposit.INSTANCE;
      case 102 -> JupiterLendRedeem.INSTANCE;
      case 103 -> DefiTuna.read(_data, i);
      case 104 -> AlphaQ.read(_data, i);
      case 105 -> RaydiumV2.INSTANCE;
      case 106 -> SarosDlmm.read(_data, i);
      case 107 -> Futarchy.read(_data, i);
      case 108 -> MeteoraDammV2WithRemainingAccounts.INSTANCE;
      case 109 -> Obsidian.INSTANCE;
      case 110 -> WhaleStreet.read(_data, i);
      case 111 -> DynamicV1.read(_data, i);
      case 112 -> PumpWrappedBuyV4.INSTANCE;
      case 113 -> PumpWrappedSellV4.INSTANCE;
      case 114 -> CarrotIssue.INSTANCE;
      case 115 -> CarrotRedeem.INSTANCE;
      case 116 -> Manifest.read(_data, i);
      case 117 -> BisonFi.read(_data, i);
      case 118 -> HumidiFiV2.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [Swap]", ordinal
      ));
    };
  }

  record Saber() implements EnumNone, Swap {

    public static final Saber INSTANCE = new Saber();

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record SaberAddDecimalsDeposit() implements EnumNone, Swap {

    public static final SaberAddDecimalsDeposit INSTANCE = new SaberAddDecimalsDeposit();

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record SaberAddDecimalsWithdraw() implements EnumNone, Swap {

    public static final SaberAddDecimalsWithdraw INSTANCE = new SaberAddDecimalsWithdraw();

    @Override
    public int ordinal() {
      return 2;
    }
  }

  record TokenSwap() implements EnumNone, Swap {

    public static final TokenSwap INSTANCE = new TokenSwap();

    @Override
    public int ordinal() {
      return 3;
    }
  }

  record Sencha() implements EnumNone, Swap {

    public static final Sencha INSTANCE = new Sencha();

    @Override
    public int ordinal() {
      return 4;
    }
  }

  record Step() implements EnumNone, Swap {

    public static final Step INSTANCE = new Step();

    @Override
    public int ordinal() {
      return 5;
    }
  }

  record Cropper() implements EnumNone, Swap {

    public static final Cropper INSTANCE = new Cropper();

    @Override
    public int ordinal() {
      return 6;
    }
  }

  record Raydium() implements EnumNone, Swap {

    public static final Raydium INSTANCE = new Raydium();

    @Override
    public int ordinal() {
      return 7;
    }
  }

  record Crema(boolean val) implements EnumBool, Swap {

    public static final Crema TRUE = new Crema(true);
    public static final Crema FALSE = new Crema(false);

    public static Crema read(final byte[] _data, int i) {
      return _data[i] == 1 ? Crema.TRUE : Crema.FALSE;
    }

    @Override
    public int ordinal() {
      return 8;
    }
  }

  record Lifinity() implements EnumNone, Swap {

    public static final Lifinity INSTANCE = new Lifinity();

    @Override
    public int ordinal() {
      return 9;
    }
  }

  record Mercurial() implements EnumNone, Swap {

    public static final Mercurial INSTANCE = new Mercurial();

    @Override
    public int ordinal() {
      return 10;
    }
  }

  record Cykura() implements EnumNone, Swap {

    public static final Cykura INSTANCE = new Cykura();

    @Override
    public int ordinal() {
      return 11;
    }
  }

  record Serum(Side val) implements BorshEnum, Swap {

    public static Serum read(final byte[] _data, final int offset) {
      return new Serum(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 12;
    }
  }

  record MarinadeDeposit() implements EnumNone, Swap {

    public static final MarinadeDeposit INSTANCE = new MarinadeDeposit();

    @Override
    public int ordinal() {
      return 13;
    }
  }

  record MarinadeUnstake() implements EnumNone, Swap {

    public static final MarinadeUnstake INSTANCE = new MarinadeUnstake();

    @Override
    public int ordinal() {
      return 14;
    }
  }

  record Aldrin(Side val) implements BorshEnum, Swap {

    public static Aldrin read(final byte[] _data, final int offset) {
      return new Aldrin(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 15;
    }
  }

  record AldrinV2(Side val) implements BorshEnum, Swap {

    public static AldrinV2 read(final byte[] _data, final int offset) {
      return new AldrinV2(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 16;
    }
  }

  record Whirlpool(boolean val) implements EnumBool, Swap {

    public static final Whirlpool TRUE = new Whirlpool(true);
    public static final Whirlpool FALSE = new Whirlpool(false);

    public static Whirlpool read(final byte[] _data, int i) {
      return _data[i] == 1 ? Whirlpool.TRUE : Whirlpool.FALSE;
    }

    @Override
    public int ordinal() {
      return 17;
    }
  }

  record Invariant(boolean val) implements EnumBool, Swap {

    public static final Invariant TRUE = new Invariant(true);
    public static final Invariant FALSE = new Invariant(false);

    public static Invariant read(final byte[] _data, int i) {
      return _data[i] == 1 ? Invariant.TRUE : Invariant.FALSE;
    }

    @Override
    public int ordinal() {
      return 18;
    }
  }

  record Meteora() implements EnumNone, Swap {

    public static final Meteora INSTANCE = new Meteora();

    @Override
    public int ordinal() {
      return 19;
    }
  }

  record GooseFX() implements EnumNone, Swap {

    public static final GooseFX INSTANCE = new GooseFX();

    @Override
    public int ordinal() {
      return 20;
    }
  }

  record DeltaFi(boolean val) implements EnumBool, Swap {

    public static final DeltaFi TRUE = new DeltaFi(true);
    public static final DeltaFi FALSE = new DeltaFi(false);

    public static DeltaFi read(final byte[] _data, int i) {
      return _data[i] == 1 ? DeltaFi.TRUE : DeltaFi.FALSE;
    }

    @Override
    public int ordinal() {
      return 21;
    }
  }

  record Balansol() implements EnumNone, Swap {

    public static final Balansol INSTANCE = new Balansol();

    @Override
    public int ordinal() {
      return 22;
    }
  }

  record MarcoPolo(boolean val) implements EnumBool, Swap {

    public static final MarcoPolo TRUE = new MarcoPolo(true);
    public static final MarcoPolo FALSE = new MarcoPolo(false);

    public static MarcoPolo read(final byte[] _data, int i) {
      return _data[i] == 1 ? MarcoPolo.TRUE : MarcoPolo.FALSE;
    }

    @Override
    public int ordinal() {
      return 23;
    }
  }

  record Dradex(Side val) implements BorshEnum, Swap {

    public static Dradex read(final byte[] _data, final int offset) {
      return new Dradex(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 24;
    }
  }

  record LifinityV2() implements EnumNone, Swap {

    public static final LifinityV2 INSTANCE = new LifinityV2();

    @Override
    public int ordinal() {
      return 25;
    }
  }

  record RaydiumClmm() implements EnumNone, Swap {

    public static final RaydiumClmm INSTANCE = new RaydiumClmm();

    @Override
    public int ordinal() {
      return 26;
    }
  }

  record Openbook(Side val) implements BorshEnum, Swap {

    public static Openbook read(final byte[] _data, final int offset) {
      return new Openbook(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 27;
    }
  }

  record Phoenix(Side val) implements BorshEnum, Swap {

    public static Phoenix read(final byte[] _data, final int offset) {
      return new Phoenix(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 28;
    }
  }

  record Symmetry(long fromTokenId, long toTokenId) implements Swap {

    public static final int BYTES = 16;

    public static Symmetry read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var fromTokenId = getInt64LE(_data, i);
      i += 8;
      final var toTokenId = getInt64LE(_data, i);
      return new Symmetry(fromTokenId, toTokenId);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      putInt64LE(_data, i, fromTokenId);
      i += 8;
      putInt64LE(_data, i, toTokenId);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 29;
    }
  }

  record TokenSwapV2() implements EnumNone, Swap {

    public static final TokenSwapV2 INSTANCE = new TokenSwapV2();

    @Override
    public int ordinal() {
      return 30;
    }
  }

  record HeliumTreasuryManagementRedeemV0() implements EnumNone, Swap {

    public static final HeliumTreasuryManagementRedeemV0 INSTANCE = new HeliumTreasuryManagementRedeemV0();

    @Override
    public int ordinal() {
      return 31;
    }
  }

  record StakeDexStakeWrappedSol() implements EnumNone, Swap {

    public static final StakeDexStakeWrappedSol INSTANCE = new StakeDexStakeWrappedSol();

    @Override
    public int ordinal() {
      return 32;
    }
  }

  record StakeDexSwapViaStake(int val) implements EnumInt32, Swap {

    public static StakeDexSwapViaStake read(final byte[] _data, int i) {
      return new StakeDexSwapViaStake(getInt32LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 33;
    }
  }

  record GooseFXV2() implements EnumNone, Swap {

    public static final GooseFXV2 INSTANCE = new GooseFXV2();

    @Override
    public int ordinal() {
      return 34;
    }
  }

  record Perps() implements EnumNone, Swap {

    public static final Perps INSTANCE = new Perps();

    @Override
    public int ordinal() {
      return 35;
    }
  }

  record PerpsAddLiquidity() implements EnumNone, Swap {

    public static final PerpsAddLiquidity INSTANCE = new PerpsAddLiquidity();

    @Override
    public int ordinal() {
      return 36;
    }
  }

  record PerpsRemoveLiquidity() implements EnumNone, Swap {

    public static final PerpsRemoveLiquidity INSTANCE = new PerpsRemoveLiquidity();

    @Override
    public int ordinal() {
      return 37;
    }
  }

  record MeteoraDlmm() implements EnumNone, Swap {

    public static final MeteoraDlmm INSTANCE = new MeteoraDlmm();

    @Override
    public int ordinal() {
      return 38;
    }
  }

  record OpenBookV2(Side val) implements BorshEnum, Swap {

    public static OpenBookV2 read(final byte[] _data, final int offset) {
      return new OpenBookV2(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 39;
    }
  }

  record RaydiumClmmV2() implements EnumNone, Swap {

    public static final RaydiumClmmV2 INSTANCE = new RaydiumClmmV2();

    @Override
    public int ordinal() {
      return 40;
    }
  }

  record StakeDexPrefundWithdrawStakeAndDepositStake(int val) implements EnumInt32, Swap {

    public static StakeDexPrefundWithdrawStakeAndDepositStake read(final byte[] _data, int i) {
      return new StakeDexPrefundWithdrawStakeAndDepositStake(getInt32LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 41;
    }
  }

  record Clone(int poolIndex,
               boolean quantityIsInput,
               boolean quantityIsCollateral) implements Swap {

    public static final int BYTES = 3;

    public static Clone read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var poolIndex = _data[i] & 0xFF;
      ++i;
      final var quantityIsInput = _data[i] == 1;
      ++i;
      final var quantityIsCollateral = _data[i] == 1;
      return new Clone(poolIndex, quantityIsInput, quantityIsCollateral);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _data[i] = (byte) poolIndex;
      ++i;
      _data[i] = (byte) (quantityIsInput ? 1 : 0);
      ++i;
      _data[i] = (byte) (quantityIsCollateral ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 42;
    }
  }

  record SanctumS(int srcLstValueCalcAccs,
                  int dstLstValueCalcAccs,
                  int srcLstIndex,
                  int dstLstIndex) implements Swap {

    public static final int BYTES = 10;

    public static SanctumS read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var srcLstValueCalcAccs = _data[i] & 0xFF;
      ++i;
      final var dstLstValueCalcAccs = _data[i] & 0xFF;
      ++i;
      final var srcLstIndex = getInt32LE(_data, i);
      i += 4;
      final var dstLstIndex = getInt32LE(_data, i);
      return new SanctumS(srcLstValueCalcAccs,
                          dstLstValueCalcAccs,
                          srcLstIndex,
                          dstLstIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _data[i] = (byte) srcLstValueCalcAccs;
      ++i;
      _data[i] = (byte) dstLstValueCalcAccs;
      ++i;
      putInt32LE(_data, i, srcLstIndex);
      i += 4;
      putInt32LE(_data, i, dstLstIndex);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 43;
    }
  }

  record SanctumSAddLiquidity(int lstValueCalcAccs, int lstIndex) implements Swap {

    public static final int BYTES = 5;

    public static SanctumSAddLiquidity read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var lstValueCalcAccs = _data[i] & 0xFF;
      ++i;
      final var lstIndex = getInt32LE(_data, i);
      return new SanctumSAddLiquidity(lstValueCalcAccs, lstIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _data[i] = (byte) lstValueCalcAccs;
      ++i;
      putInt32LE(_data, i, lstIndex);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 44;
    }
  }

  record SanctumSRemoveLiquidity(int lstValueCalcAccs, int lstIndex) implements Swap {

    public static final int BYTES = 5;

    public static SanctumSRemoveLiquidity read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var lstValueCalcAccs = _data[i] & 0xFF;
      ++i;
      final var lstIndex = getInt32LE(_data, i);
      return new SanctumSRemoveLiquidity(lstValueCalcAccs, lstIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _data[i] = (byte) lstValueCalcAccs;
      ++i;
      putInt32LE(_data, i, lstIndex);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 45;
    }
  }

  record RaydiumCP() implements EnumNone, Swap {

    public static final RaydiumCP INSTANCE = new RaydiumCP();

    @Override
    public int ordinal() {
      return 46;
    }
  }

  record WhirlpoolSwapV2(boolean aToB, RemainingAccountsInfo remainingAccountsInfo) implements Swap {

    public static WhirlpoolSwapV2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var aToB = _data[i] == 1;
      ++i;
      final var remainingAccountsInfo = _data[i++] == 0 ? null : RemainingAccountsInfo.read(_data, i);
      return new WhirlpoolSwapV2(aToB, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _data[i] = (byte) (aToB ? 1 : 0);
      ++i;
      i += Borsh.writeOptional(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + 1 + (remainingAccountsInfo == null ? 1 : (1 + Borsh.len(remainingAccountsInfo)));
    }

    @Override
    public int ordinal() {
      return 47;
    }
  }

  record OneIntro() implements EnumNone, Swap {

    public static final OneIntro INSTANCE = new OneIntro();

    @Override
    public int ordinal() {
      return 48;
    }
  }

  record PumpWrappedBuy() implements EnumNone, Swap {

    public static final PumpWrappedBuy INSTANCE = new PumpWrappedBuy();

    @Override
    public int ordinal() {
      return 49;
    }
  }

  record PumpWrappedSell() implements EnumNone, Swap {

    public static final PumpWrappedSell INSTANCE = new PumpWrappedSell();

    @Override
    public int ordinal() {
      return 50;
    }
  }

  record PerpsV2() implements EnumNone, Swap {

    public static final PerpsV2 INSTANCE = new PerpsV2();

    @Override
    public int ordinal() {
      return 51;
    }
  }

  record PerpsV2AddLiquidity() implements EnumNone, Swap {

    public static final PerpsV2AddLiquidity INSTANCE = new PerpsV2AddLiquidity();

    @Override
    public int ordinal() {
      return 52;
    }
  }

  record PerpsV2RemoveLiquidity() implements EnumNone, Swap {

    public static final PerpsV2RemoveLiquidity INSTANCE = new PerpsV2RemoveLiquidity();

    @Override
    public int ordinal() {
      return 53;
    }
  }

  record MoonshotWrappedBuy() implements EnumNone, Swap {

    public static final MoonshotWrappedBuy INSTANCE = new MoonshotWrappedBuy();

    @Override
    public int ordinal() {
      return 54;
    }
  }

  record MoonshotWrappedSell() implements EnumNone, Swap {

    public static final MoonshotWrappedSell INSTANCE = new MoonshotWrappedSell();

    @Override
    public int ordinal() {
      return 55;
    }
  }

  record StabbleStableSwap() implements EnumNone, Swap {

    public static final StabbleStableSwap INSTANCE = new StabbleStableSwap();

    @Override
    public int ordinal() {
      return 56;
    }
  }

  record StabbleWeightedSwap() implements EnumNone, Swap {

    public static final StabbleWeightedSwap INSTANCE = new StabbleWeightedSwap();

    @Override
    public int ordinal() {
      return 57;
    }
  }

  record Obric(boolean val) implements EnumBool, Swap {

    public static final Obric TRUE = new Obric(true);
    public static final Obric FALSE = new Obric(false);

    public static Obric read(final byte[] _data, int i) {
      return _data[i] == 1 ? Obric.TRUE : Obric.FALSE;
    }

    @Override
    public int ordinal() {
      return 58;
    }
  }

  record FoxBuyFromEstimatedCost() implements EnumNone, Swap {

    public static final FoxBuyFromEstimatedCost INSTANCE = new FoxBuyFromEstimatedCost();

    @Override
    public int ordinal() {
      return 59;
    }
  }

  record FoxClaimPartial(boolean val) implements EnumBool, Swap {

    public static final FoxClaimPartial TRUE = new FoxClaimPartial(true);
    public static final FoxClaimPartial FALSE = new FoxClaimPartial(false);

    public static FoxClaimPartial read(final byte[] _data, int i) {
      return _data[i] == 1 ? FoxClaimPartial.TRUE : FoxClaimPartial.FALSE;
    }

    @Override
    public int ordinal() {
      return 60;
    }
  }

  record SolFi(boolean val) implements EnumBool, Swap {

    public static final SolFi TRUE = new SolFi(true);
    public static final SolFi FALSE = new SolFi(false);

    public static SolFi read(final byte[] _data, int i) {
      return _data[i] == 1 ? SolFi.TRUE : SolFi.FALSE;
    }

    @Override
    public int ordinal() {
      return 61;
    }
  }

  record SolayerDelegateNoInit() implements EnumNone, Swap {

    public static final SolayerDelegateNoInit INSTANCE = new SolayerDelegateNoInit();

    @Override
    public int ordinal() {
      return 62;
    }
  }

  record SolayerUndelegateNoInit() implements EnumNone, Swap {

    public static final SolayerUndelegateNoInit INSTANCE = new SolayerUndelegateNoInit();

    @Override
    public int ordinal() {
      return 63;
    }
  }

  record TokenMill(Side val) implements BorshEnum, Swap {

    public static TokenMill read(final byte[] _data, final int offset) {
      return new TokenMill(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 64;
    }
  }

  record DaosFunBuy() implements EnumNone, Swap {

    public static final DaosFunBuy INSTANCE = new DaosFunBuy();

    @Override
    public int ordinal() {
      return 65;
    }
  }

  record DaosFunSell() implements EnumNone, Swap {

    public static final DaosFunSell INSTANCE = new DaosFunSell();

    @Override
    public int ordinal() {
      return 66;
    }
  }

  record ZeroFi() implements EnumNone, Swap {

    public static final ZeroFi INSTANCE = new ZeroFi();

    @Override
    public int ordinal() {
      return 67;
    }
  }

  record StakeDexWithdrawWrappedSol() implements EnumNone, Swap {

    public static final StakeDexWithdrawWrappedSol INSTANCE = new StakeDexWithdrawWrappedSol();

    @Override
    public int ordinal() {
      return 68;
    }
  }

  record VirtualsBuy() implements EnumNone, Swap {

    public static final VirtualsBuy INSTANCE = new VirtualsBuy();

    @Override
    public int ordinal() {
      return 69;
    }
  }

  record VirtualsSell() implements EnumNone, Swap {

    public static final VirtualsSell INSTANCE = new VirtualsSell();

    @Override
    public int ordinal() {
      return 70;
    }
  }

  record Perena(int inIndex, int outIndex) implements Swap {

    public static final int BYTES = 2;

    public static Perena read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var inIndex = _data[i] & 0xFF;
      ++i;
      final var outIndex = _data[i] & 0xFF;
      return new Perena(inIndex, outIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _data[i] = (byte) inIndex;
      ++i;
      _data[i] = (byte) outIndex;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 71;
    }
  }

  record PumpSwapBuy() implements EnumNone, Swap {

    public static final PumpSwapBuy INSTANCE = new PumpSwapBuy();

    @Override
    public int ordinal() {
      return 72;
    }
  }

  record PumpSwapSell() implements EnumNone, Swap {

    public static final PumpSwapSell INSTANCE = new PumpSwapSell();

    @Override
    public int ordinal() {
      return 73;
    }
  }

  record Gamma() implements EnumNone, Swap {

    public static final Gamma INSTANCE = new Gamma();

    @Override
    public int ordinal() {
      return 74;
    }
  }

  record MeteoraDlmmSwapV2(RemainingAccountsInfo val) implements BorshEnum, Swap {

    public static MeteoraDlmmSwapV2 read(final byte[] _data, final int offset) {
      return new MeteoraDlmmSwapV2(RemainingAccountsInfo.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 75;
    }
  }

  record Woofi() implements EnumNone, Swap {

    public static final Woofi INSTANCE = new Woofi();

    @Override
    public int ordinal() {
      return 76;
    }
  }

  record MeteoraDammV2() implements EnumNone, Swap {

    public static final MeteoraDammV2 INSTANCE = new MeteoraDammV2();

    @Override
    public int ordinal() {
      return 77;
    }
  }

  record MeteoraDynamicBondingCurveSwap() implements EnumNone, Swap {

    public static final MeteoraDynamicBondingCurveSwap INSTANCE = new MeteoraDynamicBondingCurveSwap();

    @Override
    public int ordinal() {
      return 78;
    }
  }

  record StabbleStableSwapV2() implements EnumNone, Swap {

    public static final StabbleStableSwapV2 INSTANCE = new StabbleStableSwapV2();

    @Override
    public int ordinal() {
      return 79;
    }
  }

  record StabbleWeightedSwapV2() implements EnumNone, Swap {

    public static final StabbleWeightedSwapV2 INSTANCE = new StabbleWeightedSwapV2();

    @Override
    public int ordinal() {
      return 80;
    }
  }

  record RaydiumLaunchlabBuy(long val) implements EnumInt64, Swap {

    public static RaydiumLaunchlabBuy read(final byte[] _data, int i) {
      return new RaydiumLaunchlabBuy(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 81;
    }
  }

  record RaydiumLaunchlabSell(long val) implements EnumInt64, Swap {

    public static RaydiumLaunchlabSell read(final byte[] _data, int i) {
      return new RaydiumLaunchlabSell(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 82;
    }
  }

  record BoopdotfunWrappedBuy() implements EnumNone, Swap {

    public static final BoopdotfunWrappedBuy INSTANCE = new BoopdotfunWrappedBuy();

    @Override
    public int ordinal() {
      return 83;
    }
  }

  record BoopdotfunWrappedSell() implements EnumNone, Swap {

    public static final BoopdotfunWrappedSell INSTANCE = new BoopdotfunWrappedSell();

    @Override
    public int ordinal() {
      return 84;
    }
  }

  record Plasma(Side val) implements BorshEnum, Swap {

    public static Plasma read(final byte[] _data, final int offset) {
      return new Plasma(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 85;
    }
  }

  record GoonFi(boolean isBid, int blacklistBump) implements Swap {

    public static final int BYTES = 2;

    public static GoonFi read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var isBid = _data[i] == 1;
      ++i;
      final var blacklistBump = _data[i] & 0xFF;
      return new GoonFi(isBid, blacklistBump);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _data[i] = (byte) (isBid ? 1 : 0);
      ++i;
      _data[i] = (byte) blacklistBump;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 86;
    }
  }

  record HumidiFi(long swapId, boolean isBaseToQuote) implements Swap {

    public static final int BYTES = 9;

    public static HumidiFi read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var swapId = getInt64LE(_data, i);
      i += 8;
      final var isBaseToQuote = _data[i] == 1;
      return new HumidiFi(swapId, isBaseToQuote);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      putInt64LE(_data, i, swapId);
      i += 8;
      _data[i] = (byte) (isBaseToQuote ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 87;
    }
  }

  record MeteoraDynamicBondingCurveSwapWithRemainingAccounts() implements EnumNone, Swap {

    public static final MeteoraDynamicBondingCurveSwapWithRemainingAccounts INSTANCE = new MeteoraDynamicBondingCurveSwapWithRemainingAccounts();

    @Override
    public int ordinal() {
      return 88;
    }
  }

  record TesseraV(Side val) implements BorshEnum, Swap {

    public static TesseraV read(final byte[] _data, final int offset) {
      return new TesseraV(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 89;
    }
  }

  record PumpWrappedBuyV2() implements EnumNone, Swap {

    public static final PumpWrappedBuyV2 INSTANCE = new PumpWrappedBuyV2();

    @Override
    public int ordinal() {
      return 90;
    }
  }

  record PumpWrappedSellV2() implements EnumNone, Swap {

    public static final PumpWrappedSellV2 INSTANCE = new PumpWrappedSellV2();

    @Override
    public int ordinal() {
      return 91;
    }
  }

  record PumpSwapBuyV2() implements EnumNone, Swap {

    public static final PumpSwapBuyV2 INSTANCE = new PumpSwapBuyV2();

    @Override
    public int ordinal() {
      return 92;
    }
  }

  record PumpSwapSellV2() implements EnumNone, Swap {

    public static final PumpSwapSellV2 INSTANCE = new PumpSwapSellV2();

    @Override
    public int ordinal() {
      return 93;
    }
  }

  record Heaven(boolean val) implements EnumBool, Swap {

    public static final Heaven TRUE = new Heaven(true);
    public static final Heaven FALSE = new Heaven(false);

    public static Heaven read(final byte[] _data, int i) {
      return _data[i] == 1 ? Heaven.TRUE : Heaven.FALSE;
    }

    @Override
    public int ordinal() {
      return 94;
    }
  }

  record SolFiV2(boolean val) implements EnumBool, Swap {

    public static final SolFiV2 TRUE = new SolFiV2(true);
    public static final SolFiV2 FALSE = new SolFiV2(false);

    public static SolFiV2 read(final byte[] _data, int i) {
      return _data[i] == 1 ? SolFiV2.TRUE : SolFiV2.FALSE;
    }

    @Override
    public int ordinal() {
      return 95;
    }
  }

  record Aquifer() implements EnumNone, Swap {

    public static final Aquifer INSTANCE = new Aquifer();

    @Override
    public int ordinal() {
      return 96;
    }
  }

  record PumpWrappedBuyV3() implements EnumNone, Swap {

    public static final PumpWrappedBuyV3 INSTANCE = new PumpWrappedBuyV3();

    @Override
    public int ordinal() {
      return 97;
    }
  }

  record PumpWrappedSellV3() implements EnumNone, Swap {

    public static final PumpWrappedSellV3 INSTANCE = new PumpWrappedSellV3();

    @Override
    public int ordinal() {
      return 98;
    }
  }

  record PumpSwapBuyV3() implements EnumNone, Swap {

    public static final PumpSwapBuyV3 INSTANCE = new PumpSwapBuyV3();

    @Override
    public int ordinal() {
      return 99;
    }
  }

  record PumpSwapSellV3() implements EnumNone, Swap {

    public static final PumpSwapSellV3 INSTANCE = new PumpSwapSellV3();

    @Override
    public int ordinal() {
      return 100;
    }
  }

  record JupiterLendDeposit() implements EnumNone, Swap {

    public static final JupiterLendDeposit INSTANCE = new JupiterLendDeposit();

    @Override
    public int ordinal() {
      return 101;
    }
  }

  record JupiterLendRedeem() implements EnumNone, Swap {

    public static final JupiterLendRedeem INSTANCE = new JupiterLendRedeem();

    @Override
    public int ordinal() {
      return 102;
    }
  }

  record DefiTuna(boolean aToB, RemainingAccountsInfo remainingAccountsInfo) implements Swap {

    public static DefiTuna read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var aToB = _data[i] == 1;
      ++i;
      final var remainingAccountsInfo = _data[i++] == 0 ? null : RemainingAccountsInfo.read(_data, i);
      return new DefiTuna(aToB, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _data[i] = (byte) (aToB ? 1 : 0);
      ++i;
      i += Borsh.writeOptional(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + 1 + (remainingAccountsInfo == null ? 1 : (1 + Borsh.len(remainingAccountsInfo)));
    }

    @Override
    public int ordinal() {
      return 103;
    }
  }

  record AlphaQ(boolean val) implements EnumBool, Swap {

    public static final AlphaQ TRUE = new AlphaQ(true);
    public static final AlphaQ FALSE = new AlphaQ(false);

    public static AlphaQ read(final byte[] _data, int i) {
      return _data[i] == 1 ? AlphaQ.TRUE : AlphaQ.FALSE;
    }

    @Override
    public int ordinal() {
      return 104;
    }
  }

  record RaydiumV2() implements EnumNone, Swap {

    public static final RaydiumV2 INSTANCE = new RaydiumV2();

    @Override
    public int ordinal() {
      return 105;
    }
  }

  record SarosDlmm(boolean val) implements EnumBool, Swap {

    public static final SarosDlmm TRUE = new SarosDlmm(true);
    public static final SarosDlmm FALSE = new SarosDlmm(false);

    public static SarosDlmm read(final byte[] _data, int i) {
      return _data[i] == 1 ? SarosDlmm.TRUE : SarosDlmm.FALSE;
    }

    @Override
    public int ordinal() {
      return 106;
    }
  }

  record Futarchy(Side val) implements BorshEnum, Swap {

    public static Futarchy read(final byte[] _data, final int offset) {
      return new Futarchy(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 107;
    }
  }

  record MeteoraDammV2WithRemainingAccounts() implements EnumNone, Swap {

    public static final MeteoraDammV2WithRemainingAccounts INSTANCE = new MeteoraDammV2WithRemainingAccounts();

    @Override
    public int ordinal() {
      return 108;
    }
  }

  record Obsidian() implements EnumNone, Swap {

    public static final Obsidian INSTANCE = new Obsidian();

    @Override
    public int ordinal() {
      return 109;
    }
  }

  record WhaleStreet(Side val) implements BorshEnum, Swap {

    public static WhaleStreet read(final byte[] _data, final int offset) {
      return new WhaleStreet(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 110;
    }
  }

  record DynamicV1(CandidateSwap[] val) implements Swap {

    public static DynamicV1 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var val = Borsh.readVector(CandidateSwap.class, CandidateSwap::read, _data, offset);
      return new DynamicV1(val);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.writeVector(val, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.lenVector(val);
    }

    @Override
    public int ordinal() {
      return 111;
    }
  }

  record PumpWrappedBuyV4() implements EnumNone, Swap {

    public static final PumpWrappedBuyV4 INSTANCE = new PumpWrappedBuyV4();

    @Override
    public int ordinal() {
      return 112;
    }
  }

  record PumpWrappedSellV4() implements EnumNone, Swap {

    public static final PumpWrappedSellV4 INSTANCE = new PumpWrappedSellV4();

    @Override
    public int ordinal() {
      return 113;
    }
  }

  record CarrotIssue() implements EnumNone, Swap {

    public static final CarrotIssue INSTANCE = new CarrotIssue();

    @Override
    public int ordinal() {
      return 114;
    }
  }

  record CarrotRedeem() implements EnumNone, Swap {

    public static final CarrotRedeem INSTANCE = new CarrotRedeem();

    @Override
    public int ordinal() {
      return 115;
    }
  }

  record Manifest(Side val) implements BorshEnum, Swap {

    public static Manifest read(final byte[] _data, final int offset) {
      return new Manifest(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 116;
    }
  }

  record BisonFi(boolean val) implements EnumBool, Swap {

    public static final BisonFi TRUE = new BisonFi(true);
    public static final BisonFi FALSE = new BisonFi(false);

    public static BisonFi read(final byte[] _data, int i) {
      return _data[i] == 1 ? BisonFi.TRUE : BisonFi.FALSE;
    }

    @Override
    public int ordinal() {
      return 117;
    }
  }

  record HumidiFiV2(long swapId, boolean isBaseToQuote) implements Swap {

    public static final int BYTES = 9;

    public static HumidiFiV2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var swapId = getInt64LE(_data, i);
      i += 8;
      final var isBaseToQuote = _data[i] == 1;
      return new HumidiFiV2(swapId, isBaseToQuote);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      putInt64LE(_data, i, swapId);
      i += 8;
      _data[i] = (byte) (isBaseToQuote ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 118;
    }
  }
}
