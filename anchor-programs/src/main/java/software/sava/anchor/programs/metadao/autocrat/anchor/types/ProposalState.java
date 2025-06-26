package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ProposalState implements Borsh.Enum {

  Pending,
  Passed,
  Failed,
  Executed;

  public static ProposalState read(final byte[] _data, final int offset) {
    return Borsh.read(ProposalState.values(), _data, offset);
  }
}