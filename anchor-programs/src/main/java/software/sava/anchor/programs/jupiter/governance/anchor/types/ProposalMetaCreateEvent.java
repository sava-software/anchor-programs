package software.sava.anchor.programs.jupiter.governance.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record ProposalMetaCreateEvent(PublicKey governor,
                                      PublicKey proposal,
                                      String title, byte[] _title,
                                      String descriptionLink, byte[] _descriptionLink) implements Borsh {

  public static ProposalMetaCreateEvent createRecord(final PublicKey governor,
                                                     final PublicKey proposal,
                                                     final String title,
                                                     final String descriptionLink) {
    return new ProposalMetaCreateEvent(governor,
                                       proposal,
                                       title, title.getBytes(UTF_8),
                                       descriptionLink, descriptionLink.getBytes(UTF_8));
  }

  public static ProposalMetaCreateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var title = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var descriptionLink = Borsh.string(_data, i);
    return new ProposalMetaCreateEvent(governor,
                                       proposal,
                                       title, title.getBytes(UTF_8),
                                       descriptionLink, descriptionLink.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    proposal.write(_data, i);
    i += 32;
    i += Borsh.writeVector(_title, _data, i);
    i += Borsh.writeVector(_descriptionLink, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + 32 + Borsh.lenVector(_title) + Borsh.lenVector(_descriptionLink);
  }
}
