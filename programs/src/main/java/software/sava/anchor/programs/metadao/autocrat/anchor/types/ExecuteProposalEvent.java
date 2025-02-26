package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ExecuteProposalEvent(CommonFields common,
                                   PublicKey proposal,
                                   PublicKey dao) implements Borsh {

  public static final int BYTES = 80;

  public static ExecuteProposalEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var dao = readPubKey(_data, i);
    return new ExecuteProposalEvent(common, proposal, dao);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    proposal.write(_data, i);
    i += 32;
    dao.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
