package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public record OracleGuardRails(PriceDivergenceGuardRails priceDivergence, ValidityGuardRails validity) implements Borsh {

  public static OracleGuardRails read(final byte[] _data, final int offset) {
    int i = offset;
    final var priceDivergence = PriceDivergenceGuardRails.read(_data, i);
    i += Borsh.len(priceDivergence);
    final var validity = ValidityGuardRails.read(_data, i);
    return new OracleGuardRails(priceDivergence, validity);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(priceDivergence, _data, i);
    i += Borsh.write(validity, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(priceDivergence) + Borsh.len(validity);
  }
}
