package software.sava.anchor.programs.glam.protocol.anchor.types;

import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record ExtraParams(ActionType actionType,
                          PublicKey pubkey,
                          OptionalLong amount) implements Borsh {

  public static ExtraParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var actionType = ActionType.read(_data, i);
    i += Borsh.len(actionType);
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var amount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new ExtraParams(actionType, pubkey, amount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(actionType, _data, i);
    pubkey.write(_data, i);
    i += 32;
    i += Borsh.writeOptional(amount, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(actionType) + 32 + (amount == null || amount.isEmpty() ? 1 : (1 + 8));
  }
}
