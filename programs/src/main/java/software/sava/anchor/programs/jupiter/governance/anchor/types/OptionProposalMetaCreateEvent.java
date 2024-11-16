package software.sava.anchor.programs.jupiter.governance.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record OptionProposalMetaCreateEvent(PublicKey governor,
                                            PublicKey proposal,
                                            String[] optionDescriptions, byte[][] _optionDescriptions) implements Borsh {

  public static OptionProposalMetaCreateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var optionDescriptions = Borsh.readStringVector(_data, i);
    return new OptionProposalMetaCreateEvent(governor, proposal, optionDescriptions, Borsh.getBytes(optionDescriptions));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    proposal.write(_data, i);
    i += 32;
    i += Borsh.writeVector(optionDescriptions, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + 32 + Borsh.lenVector(optionDescriptions);
  }
}
