package software.sava.anchor.programs.metadao.launchpad.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LaunchFundedEvent(CommonFields common,
                                PublicKey fundingRecord,
                                PublicKey launch,
                                PublicKey funder,
                                long amount,
                                long totalCommittedByFunder,
                                long totalCommitted,
                                long fundingRecordSeqNum) implements Borsh {

  public static final int BYTES = 152;

  public static LaunchFundedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var fundingRecord = readPubKey(_data, i);
    i += 32;
    final var launch = readPubKey(_data, i);
    i += 32;
    final var funder = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var totalCommittedByFunder = getInt64LE(_data, i);
    i += 8;
    final var totalCommitted = getInt64LE(_data, i);
    i += 8;
    final var fundingRecordSeqNum = getInt64LE(_data, i);
    return new LaunchFundedEvent(common,
                                 fundingRecord,
                                 launch,
                                 funder,
                                 amount,
                                 totalCommittedByFunder,
                                 totalCommitted,
                                 fundingRecordSeqNum);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    fundingRecord.write(_data, i);
    i += 32;
    launch.write(_data, i);
    i += 32;
    funder.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, totalCommittedByFunder);
    i += 8;
    putInt64LE(_data, i, totalCommitted);
    i += 8;
    putInt64LE(_data, i, fundingRecordSeqNum);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
