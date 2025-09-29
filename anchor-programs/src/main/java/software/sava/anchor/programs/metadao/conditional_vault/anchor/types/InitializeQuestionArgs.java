package software.sava.anchor.programs.metadao.conditional_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record InitializeQuestionArgs(byte[] questionId,
                                     PublicKey oracle,
                                     int numOutcomes) implements Borsh {

  public static final int BYTES = 65;
  public static final int QUESTION_ID_LEN = 32;

  public static InitializeQuestionArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var questionId = new byte[32];
    i += Borsh.readArray(questionId, _data, i);
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var numOutcomes = _data[i] & 0xFF;
    return new InitializeQuestionArgs(questionId, oracle, numOutcomes);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(questionId, 32, _data, i);
    oracle.write(_data, i);
    i += 32;
    _data[i] = (byte) numOutcomes;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
