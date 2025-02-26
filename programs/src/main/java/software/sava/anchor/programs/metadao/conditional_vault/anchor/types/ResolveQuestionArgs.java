package software.sava.anchor.programs.metadao.conditional_vault.anchor.types;

import software.sava.core.borsh.Borsh;

public record ResolveQuestionArgs(int[] payoutNumerators) implements Borsh {

  public static ResolveQuestionArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var payoutNumerators = Borsh.readintVector(_data, offset);
    return new ResolveQuestionArgs(payoutNumerators);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(payoutNumerators, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(payoutNumerators);
  }
}
