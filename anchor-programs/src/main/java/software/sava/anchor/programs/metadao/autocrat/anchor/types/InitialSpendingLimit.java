package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitialSpendingLimit(long amountPerMonth, PublicKey[] members) implements Borsh {

  public static InitialSpendingLimit read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountPerMonth = getInt64LE(_data, i);
    i += 8;
    final var members = Borsh.readPublicKeyVector(_data, i);
    return new InitialSpendingLimit(amountPerMonth, members);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountPerMonth);
    i += 8;
    i += Borsh.writeVector(members, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.lenVector(members);
  }
}
