package software.sava.anchor.programs.jupiter.governance.anchor.types;

import java.lang.String;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

// Metadata about an option proposal.
public record OptionProposalMeta(PublicKey _address,
                                 Discriminator discriminator,
                                 // The [Proposal].
                                 PublicKey proposal,
                                 // description for options
                                 String[] optionDescriptions, byte[][] _optionDescriptions) implements Borsh {

  public static final int PROPOSAL_OFFSET = 8;
  public static final int OPTION_DESCRIPTIONS_OFFSET = 40;

  public static Filter createProposalFilter(final PublicKey proposal) {
    return Filter.createMemCompFilter(PROPOSAL_OFFSET, proposal);
  }

  public static OptionProposalMeta read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static OptionProposalMeta read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static OptionProposalMeta read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], OptionProposalMeta> FACTORY = OptionProposalMeta::read;

  public static OptionProposalMeta read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var optionDescriptions = Borsh.readStringVector(_data, i);
    return new OptionProposalMeta(_address, discriminator, proposal, optionDescriptions, Borsh.getBytes(optionDescriptions));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    proposal.write(_data, i);
    i += 32;
    i += Borsh.writeVector(optionDescriptions, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32 + Borsh.lenVector(optionDescriptions);
  }
}
