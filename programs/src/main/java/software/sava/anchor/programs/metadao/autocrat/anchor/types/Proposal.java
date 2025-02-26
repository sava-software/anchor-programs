package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import java.lang.String;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Proposal(PublicKey _address,
                       Discriminator discriminator,
                       int number,
                       PublicKey proposer,
                       String descriptionUrl, byte[] _descriptionUrl,
                       long slotEnqueued,
                       ProposalState state,
                       ProposalInstruction instruction,
                       PublicKey passAmm,
                       PublicKey failAmm,
                       PublicKey baseVault,
                       PublicKey quoteVault,
                       PublicKey dao,
                       long passLpTokensLocked,
                       long failLpTokensLocked,
                       // We need to include a per-proposer nonce to prevent some weird proposal
                       // front-running edge cases. Using a `u64` means that proposers are unlikely
                       // to run into collisions, even if they generate nonces randomly - I've run
                       // the math :D
                       long nonce,
                       int pdaBump,
                       PublicKey question) implements Borsh {

  public static final int NUMBER_OFFSET = 8;
  public static final int PROPOSER_OFFSET = 12;
  public static final int DESCRIPTION_URL_OFFSET = 44;

  public static Filter createNumberFilter(final int number) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, number);
    return Filter.createMemCompFilter(NUMBER_OFFSET, _data);
  }

  public static Filter createProposerFilter(final PublicKey proposer) {
    return Filter.createMemCompFilter(PROPOSER_OFFSET, proposer);
  }

  public static Proposal createRecord(final PublicKey _address,
                                      final Discriminator discriminator,
                                      final int number,
                                      final PublicKey proposer,
                                      final String descriptionUrl,
                                      final long slotEnqueued,
                                      final ProposalState state,
                                      final ProposalInstruction instruction,
                                      final PublicKey passAmm,
                                      final PublicKey failAmm,
                                      final PublicKey baseVault,
                                      final PublicKey quoteVault,
                                      final PublicKey dao,
                                      final long passLpTokensLocked,
                                      final long failLpTokensLocked,
                                      final long nonce,
                                      final int pdaBump,
                                      final PublicKey question) {
    return new Proposal(_address,
                        discriminator,
                        number,
                        proposer,
                        descriptionUrl, descriptionUrl.getBytes(UTF_8),
                        slotEnqueued,
                        state,
                        instruction,
                        passAmm,
                        failAmm,
                        baseVault,
                        quoteVault,
                        dao,
                        passLpTokensLocked,
                        failLpTokensLocked,
                        nonce,
                        pdaBump,
                        question);
  }

  public static Proposal read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Proposal read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Proposal read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Proposal> FACTORY = Proposal::read;

  public static Proposal read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var number = getInt32LE(_data, i);
    i += 4;
    final var proposer = readPubKey(_data, i);
    i += 32;
    final var descriptionUrl = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var slotEnqueued = getInt64LE(_data, i);
    i += 8;
    final var state = ProposalState.read(_data, i);
    i += Borsh.len(state);
    final var instruction = ProposalInstruction.read(_data, i);
    i += Borsh.len(instruction);
    final var passAmm = readPubKey(_data, i);
    i += 32;
    final var failAmm = readPubKey(_data, i);
    i += 32;
    final var baseVault = readPubKey(_data, i);
    i += 32;
    final var quoteVault = readPubKey(_data, i);
    i += 32;
    final var dao = readPubKey(_data, i);
    i += 32;
    final var passLpTokensLocked = getInt64LE(_data, i);
    i += 8;
    final var failLpTokensLocked = getInt64LE(_data, i);
    i += 8;
    final var nonce = getInt64LE(_data, i);
    i += 8;
    final var pdaBump = _data[i] & 0xFF;
    ++i;
    final var question = readPubKey(_data, i);
    return new Proposal(_address,
                        discriminator,
                        number,
                        proposer,
                        descriptionUrl, descriptionUrl.getBytes(UTF_8),
                        slotEnqueued,
                        state,
                        instruction,
                        passAmm,
                        failAmm,
                        baseVault,
                        quoteVault,
                        dao,
                        passLpTokensLocked,
                        failLpTokensLocked,
                        nonce,
                        pdaBump,
                        question);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt32LE(_data, i, number);
    i += 4;
    proposer.write(_data, i);
    i += 32;
    i += Borsh.writeVector(_descriptionUrl, _data, i);
    putInt64LE(_data, i, slotEnqueued);
    i += 8;
    i += Borsh.write(state, _data, i);
    i += Borsh.write(instruction, _data, i);
    passAmm.write(_data, i);
    i += 32;
    failAmm.write(_data, i);
    i += 32;
    baseVault.write(_data, i);
    i += 32;
    quoteVault.write(_data, i);
    i += 32;
    dao.write(_data, i);
    i += 32;
    putInt64LE(_data, i, passLpTokensLocked);
    i += 8;
    putInt64LE(_data, i, failLpTokensLocked);
    i += 8;
    putInt64LE(_data, i, nonce);
    i += 8;
    _data[i] = (byte) pdaBump;
    ++i;
    question.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 4
         + 32
         + Borsh.lenVector(_descriptionUrl)
         + 8
         + Borsh.len(state)
         + Borsh.len(instruction)
         + 32
         + 32
         + 32
         + 32
         + 32
         + 8
         + 8
         + 8
         + 1
         + 32;
  }
}
