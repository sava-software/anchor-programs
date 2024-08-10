package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OrderRecord(long ts,
                          PublicKey user,
                          Order order) implements Borsh {


  public static OrderRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var user = readPubKey(_data, i);
    i += 32;
    final var order = Order.read(_data, i);
    return new OrderRecord(ts, user, order);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    user.write(_data, i);
    i += 32;
    i += Borsh.write(order, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32 + Borsh.len(order);
  }
}