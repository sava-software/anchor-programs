package software.sava.anchor.programs.metadao.launchpad.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LaunchInitializedEvent(CommonFields common,
                                     PublicKey launch,
                                     long minimumRaiseAmount,
                                     PublicKey launchAuthority,
                                     PublicKey launchSigner,
                                     int launchSignerPdaBump,
                                     PublicKey launchUsdcVault,
                                     PublicKey launchTokenVault,
                                     PublicKey baseMint,
                                     PublicKey quoteMint,
                                     int pdaBump,
                                     int secondsForLaunch) implements Borsh {

  public static final int BYTES = 262;

  public static LaunchInitializedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var launch = readPubKey(_data, i);
    i += 32;
    final var minimumRaiseAmount = getInt64LE(_data, i);
    i += 8;
    final var launchAuthority = readPubKey(_data, i);
    i += 32;
    final var launchSigner = readPubKey(_data, i);
    i += 32;
    final var launchSignerPdaBump = _data[i] & 0xFF;
    ++i;
    final var launchUsdcVault = readPubKey(_data, i);
    i += 32;
    final var launchTokenVault = readPubKey(_data, i);
    i += 32;
    final var baseMint = readPubKey(_data, i);
    i += 32;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var pdaBump = _data[i] & 0xFF;
    ++i;
    final var secondsForLaunch = getInt32LE(_data, i);
    return new LaunchInitializedEvent(common,
                                      launch,
                                      minimumRaiseAmount,
                                      launchAuthority,
                                      launchSigner,
                                      launchSignerPdaBump,
                                      launchUsdcVault,
                                      launchTokenVault,
                                      baseMint,
                                      quoteMint,
                                      pdaBump,
                                      secondsForLaunch);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    launch.write(_data, i);
    i += 32;
    putInt64LE(_data, i, minimumRaiseAmount);
    i += 8;
    launchAuthority.write(_data, i);
    i += 32;
    launchSigner.write(_data, i);
    i += 32;
    _data[i] = (byte) launchSignerPdaBump;
    ++i;
    launchUsdcVault.write(_data, i);
    i += 32;
    launchTokenVault.write(_data, i);
    i += 32;
    baseMint.write(_data, i);
    i += 32;
    quoteMint.write(_data, i);
    i += 32;
    _data[i] = (byte) pdaBump;
    ++i;
    putInt32LE(_data, i, secondsForLaunch);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
