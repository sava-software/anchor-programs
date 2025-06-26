package software.sava.anchor.programs.metadao.amm.anchor.types;

import software.sava.core.borsh.Borsh;

public record CrankThatTwapEvent(CommonFields common) implements Borsh {

  public static final int BYTES = 152;

  public static CrankThatTwapEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var common = CommonFields.read(_data, offset);
    return new CrankThatTwapEvent(common);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
