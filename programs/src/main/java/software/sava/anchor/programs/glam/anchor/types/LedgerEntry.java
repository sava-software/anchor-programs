package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LedgerEntry(PublicKey user,
                          long createdAt,
                          long fulfilledAt,
                          TimeUnit timeUnit,
                          LedgerEntryKind kind,
                          PubkeyAmount incoming,
                          long value,
                          PubkeyAmount outgoing) implements Borsh {

  public static LedgerEntry read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var user = readPubKey(_data, i);
    i += 32;
    final var createdAt = getInt64LE(_data, i);
    i += 8;
    final var fulfilledAt = getInt64LE(_data, i);
    i += 8;
    final var timeUnit = TimeUnit.read(_data, i);
    i += Borsh.len(timeUnit);
    final var kind = LedgerEntryKind.read(_data, i);
    i += Borsh.len(kind);
    final var incoming = PubkeyAmount.read(_data, i);
    i += Borsh.len(incoming);
    final var value = getInt64LE(_data, i);
    i += 8;
    final var outgoing = _data[i++] == 0 ? null : PubkeyAmount.read(_data, i);
    return new LedgerEntry(user,
                           createdAt,
                           fulfilledAt,
                           timeUnit,
                           kind,
                           incoming,
                           value,
                           outgoing);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    user.write(_data, i);
    i += 32;
    putInt64LE(_data, i, createdAt);
    i += 8;
    putInt64LE(_data, i, fulfilledAt);
    i += 8;
    i += Borsh.write(timeUnit, _data, i);
    i += Borsh.write(kind, _data, i);
    i += Borsh.write(incoming, _data, i);
    putInt64LE(_data, i, value);
    i += 8;
    i += Borsh.writeOptional(outgoing, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 8
         + 8
         + Borsh.len(timeUnit)
         + Borsh.len(kind)
         + Borsh.len(incoming)
         + 8
         + (outgoing == null ? 1 : (1 + Borsh.len(outgoing)));
  }
}
