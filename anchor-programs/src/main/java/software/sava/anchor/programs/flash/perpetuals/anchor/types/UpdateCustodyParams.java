package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UpdateCustodyParams(long minReserveUsd, long limitPriceBufferBps) implements Borsh {

  public static final int BYTES = 16;

  public static UpdateCustodyParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var minReserveUsd = getInt64LE(_data, i);
    i += 8;
    final var limitPriceBufferBps = getInt64LE(_data, i);
    return new UpdateCustodyParams(minReserveUsd, limitPriceBufferBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, minReserveUsd);
    i += 8;
    putInt64LE(_data, i, limitPriceBufferBps);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
