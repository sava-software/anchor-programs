package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.borsh.Borsh;

// Proposal type
public enum ProposalType implements Borsh.Enum {

  YesNo,
  Option;

  public static ProposalType read(final byte[] _data, final int offset) {
    return Borsh.read(ProposalType.values(), _data, offset);
  }
}