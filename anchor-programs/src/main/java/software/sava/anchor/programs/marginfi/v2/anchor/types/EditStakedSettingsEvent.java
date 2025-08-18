package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record EditStakedSettingsEvent(PublicKey group, StakedSettingsEditConfig settings) implements Borsh {

  public static EditStakedSettingsEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var group = readPubKey(_data, i);
    i += 32;
    final var settings = StakedSettingsEditConfig.read(_data, i);
    return new EditStakedSettingsEvent(group, settings);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    group.write(_data, i);
    i += 32;
    i += Borsh.write(settings, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + Borsh.len(settings);
  }
}
