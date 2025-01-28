package software.sava.anchor.programs.glam_v0.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ProposalInstruction(PublicKey programId,
                                  ProposalAccountMeta[] keys,
                                  byte[] data) implements Borsh {

  public static ProposalInstruction read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var programId = readPubKey(_data, i);
    i += 32;
    final var keys = Borsh.readVector(ProposalAccountMeta.class, ProposalAccountMeta::read, _data, i);
    i += Borsh.lenVector(keys);
    final byte[] data = Borsh.readbyteVector(_data, i);
    return new ProposalInstruction(programId, keys, data);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    programId.write(_data, i);
    i += 32;
    i += Borsh.writeVector(keys, _data, i);
    i += Borsh.writeVector(data, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + Borsh.lenVector(keys) + Borsh.lenVector(data);
  }
}
