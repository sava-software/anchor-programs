package software.sava.anchor.programs.metadao.launchpad.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LaunchStartedEvent(CommonFields common,
                                 PublicKey launch,
                                 PublicKey launchAuthority,
                                 long slotStarted) implements Borsh {

  public static final int BYTES = 96;

  public static LaunchStartedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var launch = readPubKey(_data, i);
    i += 32;
    final var launchAuthority = readPubKey(_data, i);
    i += 32;
    final var slotStarted = getInt64LE(_data, i);
    return new LaunchStartedEvent(common,
                                  launch,
                                  launchAuthority,
                                  slotStarted);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    launch.write(_data, i);
    i += 32;
    launchAuthority.write(_data, i);
    i += 32;
    putInt64LE(_data, i, slotStarted);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
