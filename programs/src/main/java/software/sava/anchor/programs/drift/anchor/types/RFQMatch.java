package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RFQMatch(long baseAssetAmount,
                       RFQMakerOrderParams makerOrderParams,
                       byte[] makerSignature) implements Borsh {

  public static final int BYTES = 142;

  public static RFQMatch read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var baseAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var makerOrderParams = RFQMakerOrderParams.read(_data, i);
    i += Borsh.len(makerOrderParams);
    final var makerSignature = new byte[64];
    Borsh.readArray(makerSignature, _data, i);
    return new RFQMatch(baseAssetAmount, makerOrderParams, makerSignature);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, baseAssetAmount);
    i += 8;
    i += Borsh.write(makerOrderParams, _data, i);
    i += Borsh.writeArray(makerSignature, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
