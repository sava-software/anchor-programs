package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record GetOraclePriceParams() implements Borsh {

  private static final GetOraclePriceParams INSTANCE = new GetOraclePriceParams();

  public static GetOraclePriceParams read(final byte[] _data, final int offset) {
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
