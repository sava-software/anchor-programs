package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AutoAddValidatorEvent(long validatorListIndex, PublicKey voteAccount) implements Borsh {

  public static final int BYTES = 40;

  public static AutoAddValidatorEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var validatorListIndex = getInt64LE(_data, i);
    i += 8;
    final var voteAccount = readPubKey(_data, i);
    return new AutoAddValidatorEvent(validatorListIndex, voteAccount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, validatorListIndex);
    i += 8;
    voteAccount.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
