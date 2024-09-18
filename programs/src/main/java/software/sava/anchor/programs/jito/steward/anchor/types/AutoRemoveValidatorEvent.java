package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AutoRemoveValidatorEvent(long validatorListIndex,
                                       PublicKey voteAccount,
                                       boolean voteAccountClosed,
                                       boolean stakeAccountDeactivated,
                                       boolean markedForImmediateRemoval) implements Borsh {

  public static final int BYTES = 43;

  public static AutoRemoveValidatorEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var validatorListIndex = getInt64LE(_data, i);
    i += 8;
    final var voteAccount = readPubKey(_data, i);
    i += 32;
    final var voteAccountClosed = _data[i] == 1;
    ++i;
    final var stakeAccountDeactivated = _data[i] == 1;
    ++i;
    final var markedForImmediateRemoval = _data[i] == 1;
    return new AutoRemoveValidatorEvent(validatorListIndex,
                                        voteAccount,
                                        voteAccountClosed,
                                        stakeAccountDeactivated,
                                        markedForImmediateRemoval);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, validatorListIndex);
    i += 8;
    voteAccount.write(_data, i);
    i += 32;
    _data[i] = (byte) (voteAccountClosed ? 1 : 0);
    ++i;
    _data[i] = (byte) (stakeAccountDeactivated ? 1 : 0);
    ++i;
    _data[i] = (byte) (markedForImmediateRemoval ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}