package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public record IntegrationAcl(IntegrationName name, IntegrationFeature[] features) implements Borsh {

  public static IntegrationAcl read(final byte[] _data, final int offset) {
    int i = offset;
    final var name = IntegrationName.read(_data, i);
    i += Borsh.len(name);
    final var features = Borsh.readVector(IntegrationFeature.class, IntegrationFeature::read, _data, i);
    return new IntegrationAcl(name, features);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(name, _data, i);
    i += Borsh.write(features, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(name) + Borsh.len(features);
  }
}