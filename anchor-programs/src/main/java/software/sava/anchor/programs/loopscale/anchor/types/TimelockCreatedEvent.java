package software.sava.anchor.programs.loopscale.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TimelockCreatedEvent(String timelockAddress, byte[] _timelockAddress,
                                   String vaultAddress, byte[] _vaultAddress,
                                   TimelockUpdateParams timelockParams,
                                   long timelockInitTimestamp,
                                   long timelockExecutionDelay) implements Borsh {

  public static TimelockCreatedEvent createRecord(final String timelockAddress,
                                                  final String vaultAddress,
                                                  final TimelockUpdateParams timelockParams,
                                                  final long timelockInitTimestamp,
                                                  final long timelockExecutionDelay) {
    return new TimelockCreatedEvent(timelockAddress, timelockAddress.getBytes(UTF_8),
                                    vaultAddress, vaultAddress.getBytes(UTF_8),
                                    timelockParams,
                                    timelockInitTimestamp,
                                    timelockExecutionDelay);
  }

  public static TimelockCreatedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var timelockAddress = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var vaultAddress = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var timelockParams = TimelockUpdateParams.read(_data, i);
    i += Borsh.len(timelockParams);
    final var timelockInitTimestamp = getInt64LE(_data, i);
    i += 8;
    final var timelockExecutionDelay = getInt64LE(_data, i);
    return new TimelockCreatedEvent(timelockAddress, timelockAddress.getBytes(UTF_8),
                                    vaultAddress, vaultAddress.getBytes(UTF_8),
                                    timelockParams,
                                    timelockInitTimestamp,
                                    timelockExecutionDelay);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_timelockAddress, _data, i);
    i += Borsh.writeVector(_vaultAddress, _data, i);
    i += Borsh.write(timelockParams, _data, i);
    putInt64LE(_data, i, timelockInitTimestamp);
    i += 8;
    putInt64LE(_data, i, timelockExecutionDelay);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_timelockAddress)
         + Borsh.lenVector(_vaultAddress)
         + Borsh.len(timelockParams)
         + 8
         + 8;
  }
}
