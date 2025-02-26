package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ProposalInstruction(PublicKey programId,
                                  ProposalAccount[] accounts,
                                  byte[] data) implements Borsh {

  public static ProposalInstruction read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var programId = readPubKey(_data, i);
    i += 32;
    final var accounts = Borsh.readVector(ProposalAccount.class, ProposalAccount::read, _data, i);
    i += Borsh.lenVector(accounts);
    final byte[] data = Borsh.readbyteVector(_data, i);
    return new ProposalInstruction(programId, accounts, data);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    programId.write(_data, i);
    i += 32;
    i += Borsh.writeVector(accounts, _data, i);
    i += Borsh.writeVector(data, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + Borsh.lenVector(accounts) + Borsh.lenVector(data);
  }
}
