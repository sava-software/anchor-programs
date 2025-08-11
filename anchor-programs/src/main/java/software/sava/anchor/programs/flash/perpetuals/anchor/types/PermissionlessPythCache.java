package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record PermissionlessPythCache(BackupOracle[] backupCache) implements Borsh {

  public static PermissionlessPythCache read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var backupCache = Borsh.readVector(BackupOracle.class, BackupOracle::read, _data, offset);
    return new PermissionlessPythCache(backupCache);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(backupCache, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(backupCache);
  }
}
