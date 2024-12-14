package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record SbOnDemandActionsPullFeedPullFeedSubmitResponseActionSubmission(BigInteger value,
                                                                              byte[] signature,
                                                                              int recoveryId,
                                                                              int _offset) implements Borsh {

  public static final int BYTES = 82;

  public static SbOnDemandActionsPullFeedPullFeedSubmitResponseActionSubmission read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var value = getInt128LE(_data, i);
    i += 16;
    final var signature = new byte[64];
    i += Borsh.readArray(signature, _data, i);
    final var recoveryId = _data[i] & 0xFF;
    ++i;
    final var _offset = _data[i] & 0xFF;
    return new SbOnDemandActionsPullFeedPullFeedSubmitResponseActionSubmission(value,
                                                                               signature,
                                                                               recoveryId,
                                                                               _offset);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, value);
    i += 16;
    i += Borsh.writeArray(signature, _data, i);
    _data[i] = (byte) recoveryId;
    ++i;
    _data[i] = (byte) _offset;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
