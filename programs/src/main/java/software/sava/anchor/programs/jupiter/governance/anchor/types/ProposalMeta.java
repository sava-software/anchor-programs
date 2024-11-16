package software.sava.anchor.programs.jupiter.governance.anchor.types;

import java.lang.String;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

// Metadata about a proposal.
public record ProposalMeta(PublicKey _address,
                           Discriminator discriminator,
                           // The [Proposal].
                           PublicKey proposal,
                           // Title of the proposal.
                           String title, byte[] _title,
                           // Link to a description of the proposal.
                           String descriptionLink, byte[] _descriptionLink) implements Borsh {

  public static final int PROPOSAL_OFFSET = 8;
  public static final int TITLE_OFFSET = 40;

  public static Filter createProposalFilter(final PublicKey proposal) {
    return Filter.createMemCompFilter(PROPOSAL_OFFSET, proposal);
  }

  public static ProposalMeta createRecord(final PublicKey _address,
                                          final Discriminator discriminator,
                                          final PublicKey proposal,
                                          final String title,
                                          final String descriptionLink) {
    return new ProposalMeta(_address, discriminator, proposal, title, title.getBytes(UTF_8), descriptionLink, descriptionLink.getBytes(UTF_8));
  }

  public static ProposalMeta read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ProposalMeta read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ProposalMeta> FACTORY = ProposalMeta::read;

  public static ProposalMeta read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var title = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var descriptionLink = Borsh.string(_data, i);
    return new ProposalMeta(_address, discriminator, proposal, title, title.getBytes(UTF_8), descriptionLink, descriptionLink.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    proposal.write(_data, i);
    i += 32;
    i += Borsh.writeVector(_title, _data, i);
    i += Borsh.writeVector(_descriptionLink, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32 + Borsh.lenVector(_title) + Borsh.lenVector(_descriptionLink);
  }
}
