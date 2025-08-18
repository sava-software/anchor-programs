package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MultiCollateralTermsUpdateParams(long apy, CollateralTermsIndices[] indices) implements Borsh {

  public static MultiCollateralTermsUpdateParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var apy = getInt64LE(_data, i);
    i += 8;
    final var indices = Borsh.readVector(CollateralTermsIndices.class, CollateralTermsIndices::read, _data, i);
    return new MultiCollateralTermsUpdateParams(apy, indices);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, apy);
    i += 8;
    i += Borsh.writeVector(indices, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.lenVector(indices);
  }
}
