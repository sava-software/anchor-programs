package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MoveProtocolFeesLog(String poolName, byte[] _poolName,
                                  long revenueAmount,
                                  long protocolFee,
                                  long revenueFeeShare,
                                  long[] padding) implements Borsh {

  public static final int PADDING_LEN = 2;
  public static MoveProtocolFeesLog createRecord(final String poolName,
                                                 final long revenueAmount,
                                                 final long protocolFee,
                                                 final long revenueFeeShare,
                                                 final long[] padding) {
    return new MoveProtocolFeesLog(poolName, poolName.getBytes(UTF_8),
                                   revenueAmount,
                                   protocolFee,
                                   revenueFeeShare,
                                   padding);
  }

  public static MoveProtocolFeesLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var revenueAmount = getInt64LE(_data, i);
    i += 8;
    final var protocolFee = getInt64LE(_data, i);
    i += 8;
    final var revenueFeeShare = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[2];
    Borsh.readArray(padding, _data, i);
    return new MoveProtocolFeesLog(poolName, poolName.getBytes(UTF_8),
                                   revenueAmount,
                                   protocolFee,
                                   revenueFeeShare,
                                   padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    putInt64LE(_data, i, revenueAmount);
    i += 8;
    putInt64LE(_data, i, protocolFee);
    i += 8;
    putInt64LE(_data, i, revenueFeeShare);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_poolName)
         + 8
         + 8
         + 8
         + Borsh.lenArray(padding);
  }
}
