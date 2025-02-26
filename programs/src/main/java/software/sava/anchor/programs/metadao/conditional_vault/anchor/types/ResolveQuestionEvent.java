package software.sava.anchor.programs.metadao.conditional_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ResolveQuestionEvent(CommonFields common,
                                   PublicKey question,
                                   int[] payoutNumerators) implements Borsh {

  public static ResolveQuestionEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var question = readPubKey(_data, i);
    i += 32;
    final var payoutNumerators = Borsh.readintVector(_data, i);
    return new ResolveQuestionEvent(common, question, payoutNumerators);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    question.write(_data, i);
    i += 32;
    i += Borsh.writeVector(payoutNumerators, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(common) + 32 + Borsh.lenVector(payoutNumerators);
  }
}
