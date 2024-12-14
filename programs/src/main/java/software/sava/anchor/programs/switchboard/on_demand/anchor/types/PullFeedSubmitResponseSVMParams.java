package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PullFeedSubmitResponseSVMParams(long slot,
                                              SbOnDemandActionsPullFeedPullFeedSubmitResponseSvmActionSubmission[] submissions,
                                              PublicKey sourceQueueKey,
                                              int queueBump) implements Borsh {

  public static PullFeedSubmitResponseSVMParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var submissions = Borsh.readVector(SbOnDemandActionsPullFeedPullFeedSubmitResponseSvmActionSubmission.class, SbOnDemandActionsPullFeedPullFeedSubmitResponseSvmActionSubmission::read, _data, i);
    i += Borsh.lenVector(submissions);
    final var sourceQueueKey = readPubKey(_data, i);
    i += 32;
    final var queueBump = _data[i] & 0xFF;
    return new PullFeedSubmitResponseSVMParams(slot,
                                               submissions,
                                               sourceQueueKey,
                                               queueBump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, slot);
    i += 8;
    i += Borsh.writeVector(submissions, _data, i);
    sourceQueueKey.write(_data, i);
    i += 32;
    _data[i] = (byte) queueBump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.lenVector(submissions) + 32 + 1;
  }
}
