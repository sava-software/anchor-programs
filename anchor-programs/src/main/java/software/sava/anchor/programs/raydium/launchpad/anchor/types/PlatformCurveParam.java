package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PlatformCurveParam(// The epoch for update interval, 0 means not update
                                 long epoch,
                                 // The curve params index
                                 int index,
                                 // The global config address
                                 PublicKey globalConfig,
                                 // bonding curve param
                                 BondingCurveParam bondingCurveParam,
                                 // padding for future updates
                                 long[] padding) implements Borsh {

  public static final int BYTES = 491;
  public static final int PADDING_LEN = 50;

  public static PlatformCurveParam read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var index = _data[i] & 0xFF;
    ++i;
    final var globalConfig = readPubKey(_data, i);
    i += 32;
    final var bondingCurveParam = BondingCurveParam.read(_data, i);
    i += Borsh.len(bondingCurveParam);
    final var padding = new long[50];
    Borsh.readArray(padding, _data, i);
    return new PlatformCurveParam(epoch,
                                  index,
                                  globalConfig,
                                  bondingCurveParam,
                                  padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, epoch);
    i += 8;
    _data[i] = (byte) index;
    ++i;
    globalConfig.write(_data, i);
    i += 32;
    i += Borsh.write(bondingCurveParam, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
