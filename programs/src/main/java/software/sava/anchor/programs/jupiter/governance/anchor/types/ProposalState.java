package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.borsh.Borsh;

// The state of a proposal.
// 
// The `expired` state from Compound is missing here, because the
// Smart Wallet handles execution.
public enum ProposalState implements Borsh.Enum {

  Draft,
  Active,
  Canceled,
  Defeated,
  Succeeded,
  Queued;

  public static ProposalState read(final byte[] _data, final int offset) {
    return Borsh.read(ProposalState.values(), _data, offset);
  }
}