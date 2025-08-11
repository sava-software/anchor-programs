package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapFeeInternalLog(String poolName, byte[] _poolName,
                                 PublicKey owner,
                                 long custodyInUid,
                                 long custodyOutUid,
                                 long swapAmount,
                                 long rewardCustodyAmount) implements Borsh {

  public static SwapFeeInternalLog createRecord(final String poolName,
                                                final PublicKey owner,
                                                final long custodyInUid,
                                                final long custodyOutUid,
                                                final long swapAmount,
                                                final long rewardCustodyAmount) {
    return new SwapFeeInternalLog(poolName, poolName.getBytes(UTF_8),
                                  owner,
                                  custodyInUid,
                                  custodyOutUid,
                                  swapAmount,
                                  rewardCustodyAmount);
  }

  public static SwapFeeInternalLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var owner = readPubKey(_data, i);
    i += 32;
    final var custodyInUid = getInt64LE(_data, i);
    i += 8;
    final var custodyOutUid = getInt64LE(_data, i);
    i += 8;
    final var swapAmount = getInt64LE(_data, i);
    i += 8;
    final var rewardCustodyAmount = getInt64LE(_data, i);
    return new SwapFeeInternalLog(poolName, poolName.getBytes(UTF_8),
                                  owner,
                                  custodyInUid,
                                  custodyOutUid,
                                  swapAmount,
                                  rewardCustodyAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, custodyInUid);
    i += 8;
    putInt64LE(_data, i, custodyOutUid);
    i += 8;
    putInt64LE(_data, i, swapAmount);
    i += 8;
    putInt64LE(_data, i, rewardCustodyAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_poolName)
         + 32
         + 8
         + 8
         + 8
         + 8;
  }
}
