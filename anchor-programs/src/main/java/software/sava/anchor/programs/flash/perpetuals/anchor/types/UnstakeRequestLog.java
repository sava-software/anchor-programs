package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UnstakeRequestLog(String poolName, byte[] _poolName,
                                PublicKey owner,
                                long amount) implements Borsh {

  public static UnstakeRequestLog createRecord(final String poolName,
                                               final PublicKey owner,
                                               final long amount) {
    return new UnstakeRequestLog(poolName, poolName.getBytes(UTF_8), owner, amount);
  }

  public static UnstakeRequestLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var owner = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    return new UnstakeRequestLog(poolName, poolName.getBytes(UTF_8), owner, amount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_poolName) + 32 + 8;
  }
}
