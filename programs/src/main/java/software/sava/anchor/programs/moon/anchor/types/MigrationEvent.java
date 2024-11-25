package software.sava.anchor.programs.moon.anchor.types;


import static java.nio.charset.StandardCharsets.UTF_8;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

import software.sava.core.borsh.Borsh;

public record MigrationEvent(long tokensMigrated,
                             long tokensBurned,
                             long collateralMigrated,
                             long fee,
                             String label, byte[] _label) implements Borsh {

  public static MigrationEvent createRecord(final long tokensMigrated,
                                            final long tokensBurned,
                                            final long collateralMigrated,
                                            final long fee,
                                            final String label) {
    return new MigrationEvent(tokensMigrated,
                              tokensBurned,
                              collateralMigrated,
                              fee,
                              label, label.getBytes(UTF_8));
  }

  public static MigrationEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tokensMigrated = getInt64LE(_data, i);
    i += 8;
    final var tokensBurned = getInt64LE(_data, i);
    i += 8;
    final var collateralMigrated = getInt64LE(_data, i);
    i += 8;
    final var fee = getInt64LE(_data, i);
    i += 8;
    final var label = Borsh.string(_data, i);
    return new MigrationEvent(tokensMigrated,
                              tokensBurned,
                              collateralMigrated,
                              fee,
                              label, label.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, tokensMigrated);
    i += 8;
    putInt64LE(_data, i, tokensBurned);
    i += 8;
    putInt64LE(_data, i, collateralMigrated);
    i += 8;
    putInt64LE(_data, i, fee);
    i += 8;
    i += Borsh.writeVector(_label, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 8
         + 8
         + 8
         + Borsh.lenVector(_label);
  }
}
