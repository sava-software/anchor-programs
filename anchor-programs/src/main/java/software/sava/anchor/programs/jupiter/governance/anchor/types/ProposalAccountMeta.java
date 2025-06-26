package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

// Account metadata used to define Instructions
public record ProposalAccountMeta(// An account's public key
                                  PublicKey pubkey,
                                  // True if an Instruction requires a Transaction signature matching `pubkey`.
                                  boolean isSigner,
                                  // True if the `pubkey` can be loaded as a read-write account.
                                  boolean isWritable) implements Borsh {

  public static final int BYTES = 34;

  public static ProposalAccountMeta read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var isSigner = _data[i] == 1;
    ++i;
    final var isWritable = _data[i] == 1;
    return new ProposalAccountMeta(pubkey, isSigner, isWritable);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    pubkey.write(_data, i);
    i += 32;
    _data[i] = (byte) (isSigner ? 1 : 0);
    ++i;
    _data[i] = (byte) (isWritable ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
