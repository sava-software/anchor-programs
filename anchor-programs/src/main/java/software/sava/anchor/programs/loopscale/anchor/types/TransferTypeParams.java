package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.RustEnum;

public sealed interface TransferTypeParams extends RustEnum permits
  TransferTypeParams.Liquidity,
  TransferTypeParams.TokenAmounts {

  static TransferTypeParams read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Liquidity.read(_data, i);
      case 1 -> TokenAmounts.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [TransferTypeParams]", ordinal
      ));
    };
  }

  record Liquidity(LiquidityParams val) implements BorshEnum, TransferTypeParams {

    public static Liquidity read(final byte[] _data, final int offset) {
      return new Liquidity(LiquidityParams.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record TokenAmounts(TokenAmountsParams val) implements BorshEnum, TransferTypeParams {

    public static TokenAmounts read(final byte[] _data, final int offset) {
      return new TokenAmounts(TokenAmountsParams.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
