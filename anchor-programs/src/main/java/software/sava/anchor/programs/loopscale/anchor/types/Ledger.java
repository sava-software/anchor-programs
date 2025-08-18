package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record Ledger(int status,
                     PublicKey strategy,
                     PublicKey principalMint,
                     PublicKey marketInformation,
                     PodU64 principalDue,
                     PodU64 principalRepaid,
                     PodU64 interestOutstanding,
                     PodU64 lastInterestUpdatedTime,
                     Duration duration,
                     PodDecimal interestPerSecond,
                     PodU64 startTime,
                     PodU64 endTime,
                     PodU64CBPS apy) implements Borsh {

  public static final int BYTES = 182;

  public static Ledger read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var status = _data[i] & 0xFF;
    ++i;
    final var strategy = readPubKey(_data, i);
    i += 32;
    final var principalMint = readPubKey(_data, i);
    i += 32;
    final var marketInformation = readPubKey(_data, i);
    i += 32;
    final var principalDue = PodU64.read(_data, i);
    i += Borsh.len(principalDue);
    final var principalRepaid = PodU64.read(_data, i);
    i += Borsh.len(principalRepaid);
    final var interestOutstanding = PodU64.read(_data, i);
    i += Borsh.len(interestOutstanding);
    final var lastInterestUpdatedTime = PodU64.read(_data, i);
    i += Borsh.len(lastInterestUpdatedTime);
    final var duration = Duration.read(_data, i);
    i += Borsh.len(duration);
    final var interestPerSecond = PodDecimal.read(_data, i);
    i += Borsh.len(interestPerSecond);
    final var startTime = PodU64.read(_data, i);
    i += Borsh.len(startTime);
    final var endTime = PodU64.read(_data, i);
    i += Borsh.len(endTime);
    final var apy = PodU64CBPS.read(_data, i);
    return new Ledger(status,
                      strategy,
                      principalMint,
                      marketInformation,
                      principalDue,
                      principalRepaid,
                      interestOutstanding,
                      lastInterestUpdatedTime,
                      duration,
                      interestPerSecond,
                      startTime,
                      endTime,
                      apy);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) status;
    ++i;
    strategy.write(_data, i);
    i += 32;
    principalMint.write(_data, i);
    i += 32;
    marketInformation.write(_data, i);
    i += 32;
    i += Borsh.write(principalDue, _data, i);
    i += Borsh.write(principalRepaid, _data, i);
    i += Borsh.write(interestOutstanding, _data, i);
    i += Borsh.write(lastInterestUpdatedTime, _data, i);
    i += Borsh.write(duration, _data, i);
    i += Borsh.write(interestPerSecond, _data, i);
    i += Borsh.write(startTime, _data, i);
    i += Borsh.write(endTime, _data, i);
    i += Borsh.write(apy, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
