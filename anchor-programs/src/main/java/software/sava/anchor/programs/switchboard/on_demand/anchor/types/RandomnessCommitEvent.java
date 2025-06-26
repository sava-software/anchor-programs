package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RandomnessCommitEvent(PublicKey randomnessAccount,
                                    PublicKey oracle,
                                    long slot,
                                    byte[] slothash) implements Borsh {

  public static final int BYTES = 104;

  public static RandomnessCommitEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var randomnessAccount = readPubKey(_data, i);
    i += 32;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var slothash = new byte[32];
    Borsh.readArray(slothash, _data, i);
    return new RandomnessCommitEvent(randomnessAccount,
                                     oracle,
                                     slot,
                                     slothash);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    randomnessAccount.write(_data, i);
    i += 32;
    oracle.write(_data, i);
    i += 32;
    putInt64LE(_data, i, slot);
    i += 8;
    i += Borsh.writeArray(slothash, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
