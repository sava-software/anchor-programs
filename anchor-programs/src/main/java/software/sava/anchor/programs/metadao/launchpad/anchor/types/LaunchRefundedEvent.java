package software.sava.anchor.programs.metadao.launchpad.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LaunchRefundedEvent(CommonFields common,
                                  PublicKey launch,
                                  PublicKey funder,
                                  long usdcRefunded,
                                  PublicKey fundingRecord) implements Borsh {

  public static final int BYTES = 128;

  public static LaunchRefundedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var launch = readPubKey(_data, i);
    i += 32;
    final var funder = readPubKey(_data, i);
    i += 32;
    final var usdcRefunded = getInt64LE(_data, i);
    i += 8;
    final var fundingRecord = readPubKey(_data, i);
    return new LaunchRefundedEvent(common,
                                   launch,
                                   funder,
                                   usdcRefunded,
                                   fundingRecord);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    launch.write(_data, i);
    i += 32;
    funder.write(_data, i);
    i += 32;
    putInt64LE(_data, i, usdcRefunded);
    i += 8;
    fundingRecord.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
