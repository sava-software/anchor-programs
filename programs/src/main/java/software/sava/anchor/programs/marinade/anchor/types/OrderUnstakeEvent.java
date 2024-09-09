package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OrderUnstakeEvent(PublicKey state,
                                long ticketEpoch,
                                PublicKey ticket,
                                PublicKey beneficiary,
                                long circulatingTicketBalance,
                                long circulatingTicketCount,
                                long userMsolBalance,
                                long burnedMsolAmount,
                                long solAmount,
                                int feeBpCents,
                                long totalVirtualStakedLamports,
                                long msolSupply) implements Borsh {

  public static final int BYTES = 164;

  public static OrderUnstakeEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var ticketEpoch = getInt64LE(_data, i);
    i += 8;
    final var ticket = readPubKey(_data, i);
    i += 32;
    final var beneficiary = readPubKey(_data, i);
    i += 32;
    final var circulatingTicketBalance = getInt64LE(_data, i);
    i += 8;
    final var circulatingTicketCount = getInt64LE(_data, i);
    i += 8;
    final var userMsolBalance = getInt64LE(_data, i);
    i += 8;
    final var burnedMsolAmount = getInt64LE(_data, i);
    i += 8;
    final var solAmount = getInt64LE(_data, i);
    i += 8;
    final var feeBpCents = getInt32LE(_data, i);
    i += 4;
    final var totalVirtualStakedLamports = getInt64LE(_data, i);
    i += 8;
    final var msolSupply = getInt64LE(_data, i);
    return new OrderUnstakeEvent(state,
                                 ticketEpoch,
                                 ticket,
                                 beneficiary,
                                 circulatingTicketBalance,
                                 circulatingTicketCount,
                                 userMsolBalance,
                                 burnedMsolAmount,
                                 solAmount,
                                 feeBpCents,
                                 totalVirtualStakedLamports,
                                 msolSupply);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    putInt64LE(_data, i, ticketEpoch);
    i += 8;
    ticket.write(_data, i);
    i += 32;
    beneficiary.write(_data, i);
    i += 32;
    putInt64LE(_data, i, circulatingTicketBalance);
    i += 8;
    putInt64LE(_data, i, circulatingTicketCount);
    i += 8;
    putInt64LE(_data, i, userMsolBalance);
    i += 8;
    putInt64LE(_data, i, burnedMsolAmount);
    i += 8;
    putInt64LE(_data, i, solAmount);
    i += 8;
    putInt32LE(_data, i, feeBpCents);
    i += 4;
    putInt64LE(_data, i, totalVirtualStakedLamports);
    i += 8;
    putInt64LE(_data, i, msolSupply);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
