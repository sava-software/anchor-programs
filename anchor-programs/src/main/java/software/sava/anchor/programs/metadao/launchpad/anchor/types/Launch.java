package software.sava.anchor.programs.metadao.launchpad.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Launch(PublicKey _address,
                     Discriminator discriminator,
                     // The PDA bump.
                     int pdaBump,
                     // The minimum amount of USDC that must be raised, otherwise
                     // everyone can get their USDC back.
                     long minimumRaiseAmount,
                     // The monthly spending limit the DAO allocates to the team. Must be
                     // less than 1/6th of the minimum raise amount (so 6 months of burn).
                     long monthlySpendingLimitAmount,
                     // The wallets that have access to the monthly spending limit.
                     PublicKey[] monthlySpendingLimitMembers,
                     // The account that can start the launch.
                     PublicKey launchAuthority,
                     // The launch signer address. Needed because Raydium pools need a SOL payer and this PDA can't hold SOL.
                     PublicKey launchSigner,
                     // The PDA bump for the launch signer.
                     int launchSignerPdaBump,
                     // The USDC vault that will hold the USDC raised until the launch is over.
                     PublicKey launchQuoteVault,
                     // The token vault, used to send tokens to Raydium.
                     PublicKey launchBaseVault,
                     // The token that will be minted to funders and that will control the DAO.
                     PublicKey baseMint,
                     // The USDC mint.
                     PublicKey quoteMint,
                     // The unix timestamp when the launch was started.
                     long unixTimestampStarted,
                     // The amount of USDC that has been committed by the users.
                     long totalCommittedAmount,
                     // The state of the launch.
                     LaunchState state,
                     // The sequence number of this launch. Useful for sorting events.
                     long seqNum,
                     // The number of seconds that the launch will be live for.
                     int secondsForLaunch,
                     // The DAO, if the launch is complete.
                     PublicKey dao,
                     // The DAO treasury that USDC / LP is sent to, if the launch is complete.
                     PublicKey daoVault) implements Borsh {

  public static final int PDA_BUMP_OFFSET = 8;
  public static final int MINIMUM_RAISE_AMOUNT_OFFSET = 9;
  public static final int MONTHLY_SPENDING_LIMIT_AMOUNT_OFFSET = 17;
  public static final int MONTHLY_SPENDING_LIMIT_MEMBERS_OFFSET = 25;

  public static Filter createPdaBumpFilter(final int pdaBump) {
    return Filter.createMemCompFilter(PDA_BUMP_OFFSET, new byte[]{(byte) pdaBump});
  }

  public static Filter createMinimumRaiseAmountFilter(final long minimumRaiseAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minimumRaiseAmount);
    return Filter.createMemCompFilter(MINIMUM_RAISE_AMOUNT_OFFSET, _data);
  }

  public static Filter createMonthlySpendingLimitAmountFilter(final long monthlySpendingLimitAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, monthlySpendingLimitAmount);
    return Filter.createMemCompFilter(MONTHLY_SPENDING_LIMIT_AMOUNT_OFFSET, _data);
  }

  public static Launch read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Launch read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Launch read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Launch> FACTORY = Launch::read;

  public static Launch read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pdaBump = _data[i] & 0xFF;
    ++i;
    final var minimumRaiseAmount = getInt64LE(_data, i);
    i += 8;
    final var monthlySpendingLimitAmount = getInt64LE(_data, i);
    i += 8;
    final var monthlySpendingLimitMembers = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(monthlySpendingLimitMembers);
    final var launchAuthority = readPubKey(_data, i);
    i += 32;
    final var launchSigner = readPubKey(_data, i);
    i += 32;
    final var launchSignerPdaBump = _data[i] & 0xFF;
    ++i;
    final var launchQuoteVault = readPubKey(_data, i);
    i += 32;
    final var launchBaseVault = readPubKey(_data, i);
    i += 32;
    final var baseMint = readPubKey(_data, i);
    i += 32;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var unixTimestampStarted = getInt64LE(_data, i);
    i += 8;
    final var totalCommittedAmount = getInt64LE(_data, i);
    i += 8;
    final var state = LaunchState.read(_data, i);
    i += Borsh.len(state);
    final var seqNum = getInt64LE(_data, i);
    i += 8;
    final var secondsForLaunch = getInt32LE(_data, i);
    i += 4;
    final var dao = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (dao != null) {
      i += 32;
    }
    final var daoVault = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new Launch(_address,
                      discriminator,
                      pdaBump,
                      minimumRaiseAmount,
                      monthlySpendingLimitAmount,
                      monthlySpendingLimitMembers,
                      launchAuthority,
                      launchSigner,
                      launchSignerPdaBump,
                      launchQuoteVault,
                      launchBaseVault,
                      baseMint,
                      quoteMint,
                      unixTimestampStarted,
                      totalCommittedAmount,
                      state,
                      seqNum,
                      secondsForLaunch,
                      dao,
                      daoVault);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) pdaBump;
    ++i;
    putInt64LE(_data, i, minimumRaiseAmount);
    i += 8;
    putInt64LE(_data, i, monthlySpendingLimitAmount);
    i += 8;
    i += Borsh.writeVector(monthlySpendingLimitMembers, _data, i);
    launchAuthority.write(_data, i);
    i += 32;
    launchSigner.write(_data, i);
    i += 32;
    _data[i] = (byte) launchSignerPdaBump;
    ++i;
    launchQuoteVault.write(_data, i);
    i += 32;
    launchBaseVault.write(_data, i);
    i += 32;
    baseMint.write(_data, i);
    i += 32;
    quoteMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, unixTimestampStarted);
    i += 8;
    putInt64LE(_data, i, totalCommittedAmount);
    i += 8;
    i += Borsh.write(state, _data, i);
    putInt64LE(_data, i, seqNum);
    i += 8;
    putInt32LE(_data, i, secondsForLaunch);
    i += 4;
    i += Borsh.writeOptional(dao, _data, i);
    i += Borsh.writeOptional(daoVault, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 1
         + 8
         + 8
         + Borsh.lenVector(monthlySpendingLimitMembers)
         + 32
         + 32
         + 1
         + 32
         + 32
         + 32
         + 32
         + 8
         + 8
         + Borsh.len(state)
         + 8
         + 4
         + (dao == null ? 1 : (1 + 32))
         + (daoVault == null ? 1 : (1 + 32));
  }
}
