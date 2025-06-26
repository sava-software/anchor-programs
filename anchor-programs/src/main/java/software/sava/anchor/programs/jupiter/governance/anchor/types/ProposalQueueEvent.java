package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ProposalQueueEvent(PublicKey governor,
                                 PublicKey proposal,
                                 PublicKey transaction) implements Borsh {

  public static final int BYTES = 96;

  public static ProposalQueueEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var transaction = readPubKey(_data, i);
    return new ProposalQueueEvent(governor, proposal, transaction);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    proposal.write(_data, i);
    i += 32;
    transaction.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
