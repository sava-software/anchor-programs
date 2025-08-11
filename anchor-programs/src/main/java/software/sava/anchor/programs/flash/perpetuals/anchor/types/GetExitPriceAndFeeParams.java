package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record GetExitPriceAndFeeParams() implements Borsh {

  private static final GetExitPriceAndFeeParams INSTANCE = new GetExitPriceAndFeeParams();

  public static GetExitPriceAndFeeParams read(final byte[] _data, final int offset) {
    return INSTANCE;
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    return 0;
  }

  @Override
  public int l() {
    return 0;
  }
}
