package software.sava.anchor.programs.jupiter.voter.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record SetVoteDelegateEvent(PublicKey escrowOwner,
                                   PublicKey oldDelegate,
                                   PublicKey newDelegate) implements Borsh {

  public static final int BYTES = 96;

  public static SetVoteDelegateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var escrowOwner = readPubKey(_data, i);
    i += 32;
    final var oldDelegate = readPubKey(_data, i);
    i += 32;
    final var newDelegate = readPubKey(_data, i);
    return new SetVoteDelegateEvent(escrowOwner, oldDelegate, newDelegate);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    escrowOwner.write(_data, i);
    i += 32;
    oldDelegate.write(_data, i);
    i += 32;
    newDelegate.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
