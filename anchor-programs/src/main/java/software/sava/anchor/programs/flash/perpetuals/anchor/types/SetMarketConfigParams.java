package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SetMarketConfigParams(long maxPayoffBps,
                                    MarketPermissions permissions,
                                    boolean correlation) implements Borsh {

  public static final int BYTES = 13;

  public static SetMarketConfigParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var maxPayoffBps = getInt64LE(_data, i);
    i += 8;
    final var permissions = MarketPermissions.read(_data, i);
    i += Borsh.len(permissions);
    final var correlation = _data[i] == 1;
    return new SetMarketConfigParams(maxPayoffBps, permissions, correlation);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, maxPayoffBps);
    i += 8;
    i += Borsh.write(permissions, _data, i);
    _data[i] = (byte) (correlation ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
