package software.sava.anchor.programs.metadao.conditional_vault.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

// Questions represent statements about future events.
// 
// These statements include:
// - "Will this proposal pass?"
// - "Who, if anyone, will be hired?"
// - "How effective will the grant committee deem this grant?"
// 
// Questions have 2 or more possible outcomes. For a question like "will this
// proposal pass," the outcomes are "yes" and "no." For a question like "who
// will be hired," the outcomes could be "Alice," "Bob," and "neither."
// 
// Outcomes resolve to a number between 0 and 1. Binary questions like "will
// this proposal pass" have outcomes that resolve to exactly 0 or 1. You can
// also have questions with scalar outcomes. For example, the question "how
// effective will the grant committee deem this grant" could have two outcomes:
// "ineffective" and "effective." If the grant committee deems the grant 70%
// effective, the "effective" outcome would resolve to 0.7 and the "ineffective"
// outcome would resolve to 0.3.
// 
// Once resolved, the sum of all outcome resolutions is exactly 1.
public record Question(PublicKey _address,
                       Discriminator discriminator,
                       byte[] questionId,
                       PublicKey oracle,
                       int[] payoutNumerators,
                       int payoutDenominator) implements Borsh {

  public static final int QUESTION_ID_LEN = 32;
  public static final int QUESTION_ID_OFFSET = 8;
  public static final int ORACLE_OFFSET = 40;
  public static final int PAYOUT_NUMERATORS_OFFSET = 72;

  public static Filter createOracleFilter(final PublicKey oracle) {
    return Filter.createMemCompFilter(ORACLE_OFFSET, oracle);
  }

  public static Question read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Question read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Question read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Question> FACTORY = Question::read;

  public static Question read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var questionId = new byte[32];
    i += Borsh.readArray(questionId, _data, i);
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var payoutNumerators = Borsh.readintVector(_data, i);
    i += Borsh.lenVector(payoutNumerators);
    final var payoutDenominator = getInt32LE(_data, i);
    return new Question(_address,
                        discriminator,
                        questionId,
                        oracle,
                        payoutNumerators,
                        payoutDenominator);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.writeArrayChecked(questionId, 32, _data, i);
    oracle.write(_data, i);
    i += 32;
    i += Borsh.writeVector(payoutNumerators, _data, i);
    putInt32LE(_data, i, payoutDenominator);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.lenArray(questionId) + 32 + Borsh.lenVector(payoutNumerators) + 4;
  }
}
